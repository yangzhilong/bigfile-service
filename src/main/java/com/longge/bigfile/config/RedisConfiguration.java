 package com.longge.bigfile.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.longge.bigfile.util.RedisUtils;

/**
 * @author roger yang
 * @date 10/31/2019
 */
public class RedisConfiguration {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Bean
    public RedisBootstrap redis() {
        RedisUtils.setRedisTemplate(stringRedisTemplate);
        return new RedisBootstrap();
    }
    
    static class RedisBootstrap {}
}
