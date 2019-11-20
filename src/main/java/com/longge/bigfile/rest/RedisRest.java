 package com.longge.bigfile.rest;

import java.util.Map.Entry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.longge.bigfile.util.ReactiveRedisUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author roger yang
 * @date 10/31/2019
 */
@RestController
@RequestMapping("/v1/api/redis")
public class RedisRest {
    private static final String key = "key";
    
    @GetMapping("/add")
    public Flux<Boolean> add() {
        Mono<Boolean> mono1 = ReactiveRedisUtils.hashPut(key, "sub-key1", "v1");
        Mono<Boolean> mono2 = ReactiveRedisUtils.hashPut(key, "sub-key2", "v2");
        return Flux.just(mono1.block(), mono2.block());
    }
    
    @GetMapping("/query")
    public Flux<Entry<String, String>> query(@RequestParam(defaultValue = key)String key) {
        return ReactiveRedisUtils.hashGetAll(key);
    }
}
