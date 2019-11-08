package com.longge.bigfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.longge.bigfile.config.RedisConfiguration;

@SpringBootApplication
@Import(RedisConfiguration.class)
public class BigfileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigfileServiceApplication.class, args);
	}

}
