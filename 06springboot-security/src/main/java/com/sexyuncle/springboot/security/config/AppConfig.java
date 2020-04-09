package com.sexyuncle.springboot.security.config;

import com.loserico.orm.dao.JpaDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public JpaDao jpaDao() {
		return new JpaDao();
	}
}
