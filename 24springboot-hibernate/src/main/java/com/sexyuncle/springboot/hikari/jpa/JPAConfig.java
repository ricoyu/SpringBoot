package com.sexyuncle.springboot.hikari.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.loserico.orm.jpa.dao.JpaDao;

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
public class JPAConfig {

	@Bean
	public JpaDao jpaDao() {
		return new JpaDao();
	}
}
