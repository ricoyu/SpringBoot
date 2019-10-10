package com.sexyuncle.springboot.hikari.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loserico.commons.jackson.ObjectMapperFactoryBean;

@Configuration
public class JacksonConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(JacksonConfig.class);

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		ObjectMapperFactoryBean objectMapperFactoryBean = new ObjectMapperFactoryBean();
		objectMapperFactoryBean.getEnumProperties().add("code");
		objectMapperFactoryBean.getEnumProperties().add("desc");
		objectMapperFactoryBean.setEpochBased(false);
		try {
			return objectMapperFactoryBean.getObject();
		} catch (Exception e) {
			logger.error("msg", e);
		}
		return null;
	}

}
