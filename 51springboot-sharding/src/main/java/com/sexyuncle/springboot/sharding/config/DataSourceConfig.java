package com.sexyuncle.springboot.sharding.config;

import com.loserico.orm.dao.JpaDao;
import com.sexyuncle.springboot.sharding.datasource.RoutingDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

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
@Configuration
@EnableConfigurationProperties({DataSourceRoutingProperties.class})
@PropertySource("multi-datasource.properties")
public class DataSourceConfig {
	
	@Autowired
	private DataSourceRoutingProperties dataSourceRoutingProperties;
	
	@Resource
	private JpaProperties jpaProperties;
	
	@Autowired(required = false)
	private PersistenceUnitManager persistenceUnitManager;
	
	/**
	 * 这里配的是SpringBoot的属性名, 不同数据源的特定配置
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "datasource00")
	public DataSourceProperties dataSourceProperties00() {
		return new DataSourceProperties();
	}
	
	/**
	 * 这里配的是SpringBoot的属性名, 不同数据源的特定配置
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "datasource01")
	public DataSourceProperties dataSourceProperties01() {
		return new DataSourceProperties();
	}
	
	/**
	 * Hikari通用配置
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "common.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
	public DataSource dataSource00(DataSourceProperties dataSourceProperties00, HikariConfig hikariConfig) {
		return createDataSource(dataSourceProperties00, hikariConfig);
	}
	
	@Bean
	public DataSource dataSource01(DataSourceProperties dataSourceProperties01, HikariConfig hikariConfig) {
		return createDataSource(dataSourceProperties01, hikariConfig);
	}
	
	@Bean
	public DataSource dataSource(DataSource dataSource00, DataSource dataSource01) {
		RoutingDataSource dataSource = new RoutingDataSource();
		
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put("dataSource00", dataSource00);
		targetDataSources.put("dataSource01", dataSource01);
		
		//把多个数据源和RoutingDataSource进行关联
		dataSource.setTargetDataSources(targetDataSources);
		dataSource.setDefaultTargetDataSource(dataSource01);
		
		Map<Integer, String> mappings = new HashMap<>();
		mappings.put(0,"dataSource00");
		mappings.put(1,"dataSource01");
		dataSourceRoutingProperties.setDataSourceKeysMapping(mappings);
		
		return dataSource;
	}
	
	@Bean
	public PlatformTransactionManager txManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Primary
	@Bean(name = "entityManager")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
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
	
	private DataSource createDataSource(DataSourceProperties dataSourceProperties00, HikariConfig hikariConfig) {
		HikariDataSource dataSource = dataSourceProperties00.initializeDataSourceBuilder().type(HikariDataSource.class).build();
		dataSource.setPoolName(dataSourceProperties00.getName());
		dataSource.setMinimumIdle(hikariConfig.getMinimumIdle());
		dataSource.setMaximumPoolSize(hikariConfig.getMaximumPoolSize());
		dataSource.setIdleTimeout(hikariConfig.getIdleTimeout());
		dataSource.setConnectionTimeout(hikariConfig.getConnectionTimeout());
		dataSource.setDataSourceProperties(hikariConfig.getDataSourceProperties());
		log.info("bar datasource: {}", dataSourceProperties00.getUrl());
		if (StringUtils.hasText(dataSourceProperties00.getName())) {
			dataSource.setPoolName(dataSourceProperties00.getName());
		}
		return dataSource;
	}
}
