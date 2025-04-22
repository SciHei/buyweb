package com.scihei.buyweb.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scihei.buyweb.common.repositoty.Cache;
import com.scihei.buyweb.common.repositoty.constant.CacheKindConstants;
import com.scihei.buyweb.other.model.DO.UserRole;
import com.scihei.buyweb.user.repository.DO.Users;
import com.scihei.buyweb.user.repository.UserMapper;
import com.scihei.buyweb.other.repository.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Map;


@Slf4j
@SpringBootConfiguration
@EnableMethodSecurity
public class SecurityConfig {
    //HttpSecurity基本属性配置
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //csrf配置
                //关闭csrf验证
                .csrf(AbstractHttpConfigurer::disable)
                //建立授权模型
                .authorizeHttpRequests(authorization -> {
                    authorization.requestMatchers("/error").permitAll();
                    authorization.anyRequest().permitAll();
                })
                //基本认证方式
                .httpBasic(Customizer.withDefaults())
                //session配置
                //无状态授权，不需要保存session
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                //未授权前的请求保存配置
                //无状态授权，不需要保存认证前的请求
                .requestCache(AbstractHttpConfigurer::disable)
                //logout配置
                //无状态授权，凭证过期自动注销
                .logout(AbstractHttpConfigurer::disable)
                //security上下文
                //无状态授权，不需要保存上下文
                .securityContext(AbstractHttpConfigurer::disable);
        return http.build();
    }

    //实现UserDetailsService,验证凭证
    @Bean
    public UserDetailsService setUserDetailsService(){
        return new UserDetailsService() {
            @Autowired
            private UserMapper userMapper;
            @Autowired
            private UserRoleMapper userRoleMapper;
            @Autowired
            private Cache cache;
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                //查询用户账号密码，创建UserBuilder
                //从缓存里查
                Users users = (Users) cache.get(CacheKindConstants.USER, username);
                //缓存里没有则从数据库里查
                if(users == null)
                {
                    users = userMapper.selectOne(new QueryWrapper<Users>().eq("username", username));
                    //不管上是否为空，都存入缓存，防止穿透
                    cache.set(CacheKindConstants.USER, username, users);
                }

                if(users== null)
                    throw new UsernameNotFoundException("用户名不存在");
                else if(users.getAccountState() == Users.AccountState.BANNED)
                    throw new UsernameNotFoundException("该用户已被封禁");

                User.UserBuilder userBuilder = User.withUsername(users.getUsername()).password(users.getPassword());

                //查询用户角色
                //从缓存里查
                List<UserRole> roles = (List<UserRole>) cache.get(CacheKindConstants.ROLE, username);
                //缓存里没有则从数据库里查
                if(roles == null)
                {
                    roles = userRoleMapper.selectByMap(Map.of("username", username));
                    //存入缓存
                    cache.set(CacheKindConstants.ROLE, username, roles);
                }
                //给用户授予角色权限
                for (UserRole i: roles) {
                    userBuilder.roles(i.getRole());
                }
                return userBuilder.build();
            }
        };
    }

    //建立角色层次模型
    @Bean
    static RoleHierarchy roleHierarchy() {
        var hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_STORE\n" +
                "ROLE_STORE > ROLE_USER");
        return hierarchy;
    }

    //认证成功事件处理
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        log.info(success.getAuthentication().getName() + "登录成功");
    }

    //认证失败事件处理
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
        log.info("登录失败");
    }

   //授权拒绝事件
    @EventListener
    public void onFailure(AuthorizationDeniedEvent failure) {
        log.info("拒绝访问");
        log.info(failure.getAuthentication().get().getAuthorities().toString());
        log.info(failure.getAuthorizationResult().toString());
    }
}
