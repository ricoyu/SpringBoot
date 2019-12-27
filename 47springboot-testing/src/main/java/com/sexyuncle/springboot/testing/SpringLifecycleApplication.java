package com.sexyuncle.springboot.testing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.atomic.AtomicInteger;

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
@Slf4j
public class SpringLifecycleApplication {
	
	private static final AtomicInteger counter = new AtomicInteger(1);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringLifecycleApplication.class, args);
	}
}
