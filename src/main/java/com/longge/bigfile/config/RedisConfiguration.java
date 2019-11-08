 package com.longge.bigfile.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.longge.bigfile.util.RedisUtils;

/**
 * @author roger yang
 * @date 10/31/2019
 */
public class RedisConfiguration {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    @Bean
    public RedisBootstrap test() {
        RedisUtils.setRedisTemplate(stringRedisTemplate);
        return new RedisBootstrap();
    }
    
    static class RedisBootstrap {}
}
