package com.sexyuncle.springboot.errorpage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

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
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class ErrorPageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErrorPageApplication.class, args);
	}
}
