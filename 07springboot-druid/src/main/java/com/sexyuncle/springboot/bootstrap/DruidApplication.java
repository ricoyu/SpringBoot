package com.sexyuncle.springboot.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Druid监控界面，默认访问地址为：http://localhost:999/druid
 * 
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
public class DruidApplication {

	public static void main(String[] args) {
		System.out.println("你好"); 
		SpringApplication.run(DruidApplication.class, args);
	}
}
