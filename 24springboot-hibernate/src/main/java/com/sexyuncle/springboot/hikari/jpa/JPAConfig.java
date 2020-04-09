package com.sexyuncle.springboot.hikari.jpa;

import com.loserico.orm.dao.JpaDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
public class JPAConfig {

	@Bean
	public JpaDao jpaDao() {
		return new JpaDao();
	}
}
