package com.sexyuncle.springboot.scp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loserico.commons.jackson.ObjectMapperFactoryBean;

@Configuration
public class AppConfig {

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		ObjectMapperFactoryBean objectMapperFactoryBean = new ObjectMapperFactoryBean();
		objectMapperFactoryBean.getEnumProperties().add("code");
		try {
			return objectMapperFactoryBean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
