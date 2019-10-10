package com.sexyuncle.springboot.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.loserico.orm.jpa.dao.JpaDao;

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
public class H2Application {
	
	@Bean
	public JpaDao jpaDao() {
		return new JpaDao();
	}

	public static void main(String[] args) {
		SpringApplication.run(H2Application.class, args);
	}
}
