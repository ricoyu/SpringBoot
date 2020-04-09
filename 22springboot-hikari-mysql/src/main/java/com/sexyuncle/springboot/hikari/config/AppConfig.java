package com.sexyuncle.springboot.hikari.config;

import com.loserico.common.spring.transaction.TransactionEvents;
import com.loserico.orm.dao.JpaDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
