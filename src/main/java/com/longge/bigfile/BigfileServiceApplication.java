package com.longge.bigfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.longge.bigfile.annotation.EnableReactiveRedisUtilsAutoConfiguration;
import com.longge.bigfile.annotation.EnableRedisUtilsAutoConfiguration;

@SpringBootApplication
@EnableRedisUtilsAutoConfiguration
@EnableReactiveRedisUtilsAutoConfiguration
public class BigfileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigfileServiceApplication.class, args);
	}

}
