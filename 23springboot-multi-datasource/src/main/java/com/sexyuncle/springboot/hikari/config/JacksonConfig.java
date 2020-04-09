package com.sexyuncle.springboot.hikari.config;

import com.loserico.json.ObjectMapperDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(JacksonConfig.class);

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		new ObjectMapperDecorator().decorate(objectMapper);
		return objectMapper;
	}

}
