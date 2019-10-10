package com.sexyuncle.springboot.hikari.config;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.loserico.cache.spring.ApplicationContextHolder;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AppConfig {
	
	@Bean
	public ApplicationContextAware applicationContextAware() {
		return (applicationContext) -> {
			ApplicationContextHolder.setApplicationContext(applicationContext);
		};
	}

}
