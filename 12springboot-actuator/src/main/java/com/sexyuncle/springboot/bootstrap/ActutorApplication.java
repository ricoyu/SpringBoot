package com.sexyuncle.springboot.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * http://www.baeldung.com/spring-boot-actuators?utm_source=drip&utm_medium=email&utm_campaign=Latest+articles+about+Spring+%E2%80%93+on+Baeldung
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
public class ActutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActutorApplication.class, args);
	}
}
