package com.sexyuncle.springboot.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @SpringBootApplication 注解等价于以下三个注解之和
 * @Configuration, @EnableAutoConfiguration, and @ComponentScan
 * 
 * Finally, we need to of course configure our new persistence layer:
 * @EnableJpaRepositories to scan the specified package for repositories
 * @EntityScan to pick up our JPA entities
 * <p>
 * Copyright: Copyright (c) 2018-04-16 10:54
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@EnableJpaRepositories("com.sexyuncle.springboot.security.dao")
@EntityScan("com.sexyuncle.springboot.security.entity")
@SpringBootApplication
public class JPASecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(JPASecurityApplication.class, args);
	}
}
