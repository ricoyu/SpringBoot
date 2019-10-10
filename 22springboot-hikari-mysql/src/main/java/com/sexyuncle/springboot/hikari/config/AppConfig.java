package com.sexyuncle.springboot.hikari.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.loserico.orm.jpa.dao.JpaDao;
import com.peacefish.spring.transaction.TransactionEvents;

@Configuration
public class AppConfig {

	@Bean
	public JpaDao jpaDao() {
		return new JpaDao();
	}

	@Bean
	public TransactionEvents transactionEvents() {
		return new TransactionEvents();
	}
}
