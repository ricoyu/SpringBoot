package com.sexyuncle.springboot.sharding.config;

import com.loserico.orm.jpa.dao.JpaDao;
import com.sexyuncle.springboot.sharding.datasource.RoutingDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;

/**
 * <p>
 * Copyright: Copyright (c) 2018-04-17 11:15
 * <p>
 * Company: DataSense
 * <p>
 *
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@Slf4j
@PropertySource("application.properties")
@ConfigurationProperties()
@Configuration
public class JPAConfig {
	
	@Resource
	private JpaProperties jpaProperties;
	
	@Autowired
	private RoutingDataSource dataSource;
	
	@Autowired(required = false)
	private PersistenceUnitManager persistenceUnitManager;
	
	@Bean
	public PlatformTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Primary
	@Bean(name = "entityManager")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(dataSource)
				.packages("com.sexyuncle.springboot.sharding.entity")
				.build();
	}
	
	@Bean
	public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
		AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		return new EntityManagerFactoryBuilder(adapter, jpaProperties.getProperties(), persistenceUnitManager);
	}
	
	@Bean
	public JpaDao jpaDao() {
		return new JpaDao();
	}
	
	
}
