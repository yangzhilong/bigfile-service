 package com.longge.bigfile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

import com.longge.bigfile.util.ReactiveRedisUtils;

/**
 * @author roger yang
 * @date 10/31/2019
 */
public class ReactiveRedisConfiguration {
    
    @Bean
    public ReactiveStringRedisTemplate reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        ReactiveStringRedisTemplate reactiveStringRedisTemplate =  new ReactiveStringRedisTemplate(factory);
        ReactiveRedisUtils.setRedisTemplate(reactiveStringRedisTemplate);
        return reactiveStringRedisTemplate;
    }
}
