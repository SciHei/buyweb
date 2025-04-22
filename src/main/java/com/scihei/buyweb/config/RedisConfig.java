package com.scihei.buyweb.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootConfiguration
public class RedisConfig {
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    @Bean
    public RedisTemplate<String, Object> setRedisTemplate(){
        return new RedisTemplate<String, Object>(){
            {
                //设置注入的连接工厂
                setConnectionFactory(redisConnectionFactory);
                //key使用String序列化
                new StringRedisSerializer();
                setKeySerializer(StringRedisSerializer.UTF_8);
                //value使用Jackson的json序列化
                GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
                serializer.configure(mapper -> {
                    //如果涉及到对 java.time 类型的序列化，反序列化那么需要注册 JavaTimeModule
                    //设置过期时间时用到
                    mapper.registerModule(new JavaTimeModule());
                });
                setValueSerializer(serializer);
            }
        };
    }
}
