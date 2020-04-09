package com.sexyuncle.springboot.hikari.config;

import com.loserico.common.lang.context.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AppConfig {
	
	@Bean
	public ApplicationContextHolder applicationContextHolder() {
		return new ApplicationContextHolder();
	}

}
