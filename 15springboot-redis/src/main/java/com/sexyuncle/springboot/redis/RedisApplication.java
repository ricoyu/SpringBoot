package com.sexyuncle.springboot.redis;

import com.loserico.cache.JedisUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication 注解等价于以下三个注解之和
 * @Configuration, @EnableAutoConfiguration, and @ComponentScan
 * 
 * <p>
 * Copyright: Copyright (c) 2018-04-16 10:54
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@SpringBootApplication
public class RedisApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("init...");
		JedisUtils.set("k1", "v1");
	}
}
