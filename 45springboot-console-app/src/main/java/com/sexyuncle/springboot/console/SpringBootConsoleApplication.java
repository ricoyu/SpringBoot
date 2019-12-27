package com.sexyuncle.springboot.console;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CommandLineRunner is a simple Spring Boot interface with a run method. Spring Boot will automatically call the run
 * method of all beans implementing this interface after the application context has been loaded.
 *
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @SpringBootApplication 注解等价于以下三个注解之和
 * @Configuration, @EnableAutoConfiguration, and @ComponentScan
 *
 * <p>
 * Copyright: Copyright (c) 2018-04-16 10:54
 * <p>
 * Company: DataSense
 * <p>
 * @on
 */
@SpringBootApplication
@Slf4j
public class SpringBootConsoleApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		log.info("STARTING THE APPLICATION");
		SpringApplication.run(SpringBootConsoleApplication.class, args);
		log.info("APPLICATION FINISHED");
	}
	
	@Override
	public void run(String... args) throws Exception {
		log.info("EXECUTING : command line runner");
		for (int i = 0; i < args.length; ++i) {
			log.info("args[{}]: {}", i, args[i]);
		}
	}
}
