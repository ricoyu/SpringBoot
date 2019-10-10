package com.sexyuncle.springboot.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.loserico.orm.jpa.dao.JpaDao;

@Configuration
public class AppConfig {

	@Bean
	public JpaDao jpaDao() {
		return new JpaDao();
	}
}
