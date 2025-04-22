package com.scihei.buyweb.user.repository;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scihei.buyweb.user.repository.DO.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<Users> {

}
