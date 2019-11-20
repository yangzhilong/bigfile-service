 package com.longge.bigfile.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.longge.bigfile.config.ReactiveRedisConfiguration;

/**
 * @author roger yang
 * @date 11/20/2019
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ReactiveRedisConfiguration.class)
public @interface EnableReactiveRedisUtilsAutoConfiguration {

}
