package com.scihei.buyweb.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.scihei.buyweb.common.repositoty.Cache;
import com.scihei.buyweb.common.repositoty.constant.CacheKindConstants;
import com.scihei.buyweb.common.repositoty.constant.RepositoryExceptionConstants;
import com.scihei.buyweb.common.repositoty.exception.RepositoryException;
import com.scihei.buyweb.common.service.exception.ServiceException;
import com.scihei.buyweb.until.SciHei;
import com.scihei.buyweb.user.repository.DO.Users;
import com.scihei.buyweb.user.repository.UserMapper;
import com.scihei.buyweb.user.service.DTO.UserAccountDTO;
import com.scihei.buyweb.user.service.DTO.UserInfoDTO;
import com.scihei.buyweb.user.service.UserService;
import com.scihei.buyweb.user.service.constant.UserServiceExceptionConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Slf4j
@Service
//一般而言，一个方法里有两句sql就可以开启事务了，但最好还是分析逻辑决定要不要开启，也许有其他方法
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final Cache cache;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, Cache cache){
        this.userMapper = userMapper;
        this.cache = cache;
    }

    @Override
    //添加用户, 运行出错抛异常，正常修改完毕就正常返回
    public void addAccount(UserAccountDTO userAccountDTO) {
        //用户名不能为空
        if(userAccountDTO.getUsername() == null)
            throw new ServiceException(UserServiceExceptionConstants.USERNAME_NULL);
        //密码不能为空
        if(userAccountDTO.getPassword() == null)
            throw new ServiceException(UserServiceExceptionConstants.PASSWORD_NULL);

        //把异常统一为ScienceException抛出
        try {
            userMapper.insert(Users.builder()
                    .username(userAccountDTO.getUsername())
                    //密码编码后再存入Users
                    .password(SciHei.encodePassword(userAccountDTO.getPassword()))
                    //账户状态初始化为运行中
                    .accountState(Users.AccountState.RUNNING)
                    //随机生成昵称
                    .name(SciHei.getRandomName())
                    //信息状态初始化为已通过
                    .infoState(Users.InfoState.PASSED)
                    //户取创建时间
                    .addTime(SciHei.getTime()).build()
            );
        }catch (DuplicateKeyException e){
            //用户名重复
            throw new ServiceException(UserServiceExceptionConstants.USERNAME_EXISTED);
        }catch (Exception e){
            throw new ServiceException(e.toString());
        }
    }

    @Override
    @Transactional
    //删除用户, 运行出错抛异常，正常修改完毕就正常返回
    public void deleteAccount(String username) {
        if(username == null)
            throw new ServiceException(UserServiceExceptionConstants.USERNAME_NULL);

        //按用户名删除用户
        //删的时候要把以它为外键的都删了,以及其他有约束的实体
        //----



        if(userMapper.deleteByMap(Map.of("username", username)) == 0)
            //用户不存在
            throw new ServiceException(UserServiceExceptionConstants.USER_NONENTITY);
        //删除缓存中的旧数据
        cache.delete(CacheKindConstants.USER, username);
    }

    @Override
    @Transactional
    //修改用户密码, 运行出错抛异常，正常修改完毕就正常返回
    public void changePassword(String username, String newPassword, String oldPassword) {
        if(username == null)
            throw new ServiceException(UserServiceExceptionConstants.USERNAME_NULL);
        if(newPassword == null)
            throw new ServiceException(UserServiceExceptionConstants.NEW_PASSWORD_NULL);
        if(oldPassword == null)
            throw new ServiceException(UserServiceExceptionConstants.OLD_PASSWORD_NULL);

        //查找用户账户
        Users users;
        //先从缓存中查
        try{
            users = (Users) cache.get(CacheKindConstants.USER, username);
        }catch (RepositoryException e){
            //kind和key不可能为空，所以抛出异常一定是防止缓存穿透的
            //缓存表示数据库里也没有，抛出用户不存在异常
            throw new ServiceException(UserServiceExceptionConstants.USER_NONENTITY);
        }
        //没有则从数据库查
        if(users == null)
            users = userMapper.selectOne(new QueryWrapper<Users>()
                .eq("username", username)
        );

        //用户不存在
        if(users == null){
            //null也加入缓存，防止穿透
            cache.set(CacheKindConstants.USER, username, null);
            throw new ServiceException(UserServiceExceptionConstants.USER_NONENTITY);
        }

        //验证输入的密码与旧密码是否一致
        if(!BCrypt.checkpw(oldPassword, users.getPassword().substring(8)))
            throw new ServiceException(UserServiceExceptionConstants.PASSWORD_WRONG);

        //更新密码
        userMapper.update(new UpdateWrapper<Users>()
                .eq("username", username)
                .set("password", SciHei.encodePassword(newPassword))
        );
        //删除缓存中的旧数据，下一次查询的时候再把新数据加入缓存
        cache.delete(CacheKindConstants.USER, username);
    }

    @Override
    //更新用户信息, 运行出错抛异常，正常修改完毕就正常返回
    public void updateInfo(UserInfoDTO userInfoDTO) {
        if(userInfoDTO.getUsername() == null)
            throw new ServiceException(UserServiceExceptionConstants.USERNAME_NULL);

        //获得修改后的用户
        Users users = (Users) SciHei.getByCopy(userInfoDTO, Users.class);

        //按用户名修改信息
        if(userMapper.update(users, new UpdateWrapper<Users>()
                .eq("username", userInfoDTO.getUsername())
        ) == 0)
            //用户不存在
            throw new ServiceException(UserServiceExceptionConstants.USER_NONENTITY);
        //删除缓存中的旧数据，下一次查询的时候再把新数据加入缓存
        cache.delete(CacheKindConstants.USER, userInfoDTO.getUsername());
    }

    @Override
    //获得用户信息
    public UserInfoDTO getInfo(String username) {
        if(username == null)
            throw new ServiceException(UserServiceExceptionConstants.USERNAME_NULL);

        //按用户名获取用户信息
        //先从缓存查
        Users users = null;
        try{
            users = (Users) cache.get(CacheKindConstants.USER, username);
        }catch (RepositoryException e){
            //缓存表示数据库里无该数据
            if(e.getResult().equals(RepositoryExceptionConstants.VALUE_NULL))
                throw new ServiceException(UserServiceExceptionConstants.USER_NONENTITY);
            //否则再查数据库
        }

        if(users == null){
            //缓存没有则从数据库查
            users = userMapper.selectOne(new QueryWrapper<Users>()
                    .eq("username", username)
            );
            //不管是否为空，都加入缓存
            cache.set(CacheKindConstants.USER, username, users);
        }

        if(users == null)
            //用户不存在
            throw new ServiceException(UserServiceExceptionConstants.USER_NONENTITY);

        return (UserInfoDTO) SciHei.getByCopy(users, UserInfoDTO.class);
    }

    @Override
    //获得用户信息列表
    public List<UserInfoDTO> getInfoList(String fuzzyName) {
        if(fuzzyName == null)
            throw new ServiceException(UserServiceExceptionConstants.FILED_NULL);

        //按昵称模糊查询
        List<Users> usersList = userMapper.selectList(new QueryWrapper<Users>()
                .like("name", SciHei.getFuzzy(fuzzyName))
        );
        return (List<UserInfoDTO>) (Object) SciHei.getListByCopy(usersList, UserInfoDTO.class);
    }

}
