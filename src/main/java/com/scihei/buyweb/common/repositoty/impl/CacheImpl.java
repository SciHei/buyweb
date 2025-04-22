package com.scihei.buyweb.common.repositoty.impl;

import com.scihei.buyweb.common.repositoty.Cache;
import com.scihei.buyweb.common.repositoty.constant.CacheKindConstants;
import com.scihei.buyweb.common.repositoty.constant.RepositoryExceptionConstants;
import com.scihei.buyweb.common.repositoty.exception.RepositoryException;
import com.scihei.buyweb.until.SciHei;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
public class CacheImpl implements Cache {
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public CacheImpl(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }
    @Override
    public Object get(CacheKindConstants kind, String key) {
        if(key == null)
            throw new RepositoryException(RepositoryExceptionConstants.KEY_NULL);
        if(kind == null)
            throw new RepositoryException(RepositoryExceptionConstants.KIND_NULL);
        Object ret = redisTemplate.opsForValue().get(kind.name() + key);
        //如果为null说明数据库不存在该数据
        if(ret instanceof String && ret.equals("null"))
            throw new RepositoryException(RepositoryExceptionConstants.VALUE_NULL);
        return ret;
    }

    @Override
    public void set(CacheKindConstants kind, String key, Object value) {
        if(key == null)
            throw new RepositoryException(RepositoryExceptionConstants.KEY_NULL);
        if(kind == null)
            throw new RepositoryException(RepositoryExceptionConstants.KIND_NULL);

        //如果为空也加入缓存，表示数据库里不存在该数据
        if(value == null) {
            redisTemplate.opsForValue().set(kind.name() + key, "null", 60 * 3, TimeUnit.SECONDS);
        }else
            //防止缓存雪崩
            redisTemplate.opsForValue().set(kind.name() + key, value, SciHei.getRandom(60 * 10, 60 * 30), TimeUnit.SECONDS);
    }

    @Override
    public void delete(CacheKindConstants kind, String key) {
        if(key == null)
            throw new RepositoryException(RepositoryExceptionConstants.KEY_NULL);
        if(kind == null)
            throw new RepositoryException(RepositoryExceptionConstants.KIND_NULL);

        redisTemplate.delete(kind.name() + key);
    }
}
