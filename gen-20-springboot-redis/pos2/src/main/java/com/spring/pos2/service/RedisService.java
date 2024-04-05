package com.spring.pos2.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_KEY = "myKey";

    public void saveToRedis(Object data) {
        redisTemplate.opsForValue().set(REDIS_KEY, data);
    }

    public Object getFromRedis() {
        return redisTemplate.opsForValue().get(REDIS_KEY);
    }
}
