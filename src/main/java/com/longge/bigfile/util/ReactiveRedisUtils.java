 package com.longge.bigfile.util;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Range;
import org.springframework.data.domain.Range.Bound;
import org.springframework.data.redis.connection.RedisZSetCommands.Limit;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveSetOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author roger yang
 * @date 10/31/2019
 */
public class ReactiveRedisUtils {
    private static ReactiveStringRedisTemplate redisTemplate;
    private static ReactiveHashOperations<String, String, String> hashOps;
    private static ReactiveZSetOperations<String, String> zsetOps;
    private static ReactiveSetOperations<String, String> setOps;
    
    private ReactiveRedisUtils() {}
    
    public static ReactiveStringRedisTemplate getStringRedisTemplate() {
        return redisTemplate;
    }
    
    public static void setRedisTemplate(ReactiveStringRedisTemplate rt) {
        redisTemplate = rt;
        hashOps = redisTemplate.opsForHash();
        zsetOps = redisTemplate.opsForZSet();
        setOps = redisTemplate.opsForSet();
    }
    
    public static Mono<Boolean> hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
    
    public static Mono<Boolean> expire(String key, long timeout) {
        return expire(key, Duration.ofSeconds(timeout));
    }
    
    public static Mono<Boolean> expire(String key, Duration timeout) {
        return redisTemplate.expire(key, timeout);
    }
    
    /**
     *  --------------begin hash ops --------------------
     */
    public static Mono<Boolean> hasHashKey(String key, String hashKey) {
        return hashOps.hasKey(key, hashKey);
    }
    
    public static Mono<Boolean> hashPut(String key, String hashKey, String value) {
        return hashOps.put(key, hashKey, value);
    }
    
    public static Mono<Boolean> hashPutIfAbsent(String key, String hashKey, String value) {
        return hashOps.putIfAbsent(key, hashKey, value);
    }
    
    public static Mono<Boolean> hashPutAll(String key, Map<String, String> map) {
        return hashOps.putAll(key, map);
    }
    
    public static Mono<String> hashGet(String key, String hashKey) {
        return hashOps.get(key, hashKey);
    }
    
    public static Flux<Map.Entry<String, String>> hashGetAll(String key) {
        return hashOps.entries(key);
    }
    
    public static Mono<Long> hashIncrement(String key, String hashKey) {
        return hashOps.increment(key, hashKey, 1);
    }
    
    public static Mono<Long> hashIncrement(String key, String hashKey, Long value) {
        return hashOps.increment(key, hashKey, value);
    }
    
    /**
     * --------------begin zset ops --------------------
     */
    public static Mono<Long> zAddAll(String key, Set<Integer> values) {
        Set<TypedTuple<String>> tuples = values.stream().map(item -> {
            TypedTuple<String> val = new DefaultTypedTuple<>(String.valueOf(item), Double.valueOf(String.valueOf(item)));
            return val;
        }).collect(Collectors.toSet());
        return zsetOps.addAll(key, tuples);
    }
    
    public static Flux<String> zGetFirst(String key) {
        return zsetOps.range(key, Range.of(Bound.inclusive(0L), Bound.inclusive(0L)));
    }
    
    public static Flux<String> zGetNextValue(String key, double currentScore) {
        double min = currentScore + 1;
        double max = currentScore+100;
        return zsetOps.rangeByScore(key, Range.of(Bound.inclusive(min), Bound.inclusive(max)), Limit.limit().offset(0).count(1));
    }
    
    public static Mono<Double> zScore(String key, String value) {
        return zsetOps.score(key, value);
    }
    
    public static Mono<Long> zSize(String key) {
        return zsetOps.size(key);
    }
    
    public static Mono<Long> zRemove(String key, String value) {
        return zsetOps.remove(key, value);
    }
    
    public static Flux<String> zGetAll(String key) {
        return zsetOps.range(key, Range.of(Bound.inclusive(0L), Bound.inclusive(-1L)));
    }
    
    /**
     * --------------- begin set ops ----------------------
     */
    public static Mono<Long> sAdd(String key, String value) {
        return setOps.add(key, value);
    }
}
