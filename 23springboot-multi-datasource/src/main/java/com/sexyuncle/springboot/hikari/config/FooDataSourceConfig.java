package com.sexyuncle.springboot.hikari.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * http://www.voidcn.com/article/p-yykxikll-brt.html
 * <p>
 * Copyright: Copyright (c) 2019-02-19 11:50
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@Configuration
@PropertySource("multi-datasource.properties")
@Slf4j
public class FooDataSourceConfig {

	@Bean
	@ConfigurationProperties(prefix = "foo.datasource")
	public DataSourceProperties fooDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	@ConfigurationProperties(prefix = "foo.datasource.hikari")
	public HikariConfig fooHikariConfig() {
		return new HikariConfig();
	}

	@Bean
	public HikariDataSource fooDataSource(@Qualifier("fooDataSourceProperties") DataSourceProperties properties, @Qualifier("fooHikariConfig") HikariConfig hikariConfig) {
		HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
		hikariConfig.copyStateTo(dataSource);
		/*log.info("bar datasource: {}", fooDataSourceProperties().getUrl());
		if (StringUtils.hasText(properties.getName())) {
			dataSource.setPoolName(properties.getName());
		}*/
		return dataSource;
	}

	@Bean
	public DataSourceInitializer fooDataSourceInitializer(@Qualifier("fooDataSource") DataSource dataSource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource("foo-schema.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("foo-data.sql"));

		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(dataSource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		return dataSourceInitializer;
	}
}
