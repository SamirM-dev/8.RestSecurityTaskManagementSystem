package com.example.RestSecurityTaskManagementSystem.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OneStoreService {

    private final static String REDIS_PREFIX="oauth2:code:";

    private final StringRedisTemplate redis;

    public String generate(Long userId){
        String code = UUID.randomUUID().toString();
        redis.opsForValue().set(REDIS_PREFIX+code,userId.toString(), Duration.ofSeconds(60));
        return code;
    }

    public Long consume(String code){
        String userId=redis.opsForValue().get(REDIS_PREFIX+code);
        redis.delete(REDIS_PREFIX+code);
        if (userId==null){
            return null;
        }
        return Long.parseLong(userId);
    }
}
