package com.sexyuncle.springboot.sharding.config;

import com.loserico.common.lang.utils.BeanUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;

/**
 * <p>
 * Copyright: (C), 2020/2/17 12:34
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Slf4j
@Configuration
@PropertySource("application.properties")
public class DataSource01Config {
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource01")
	public DataSourceProperties properties01() {
		return new DataSourceProperties();
	}
	
	@Bean
	@Primary
	public HikariDataSource dataSource01(@Qualifier("properties01") DataSourceProperties properties, HikariConfig hikariConfig) {
		HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
		BeanUtils.copyProperties(hikariConfig, dataSource, true);
		log.info("bar datasource: {}", properties.getUrl());
		if (StringUtils.hasText(properties.getName())) {
			dataSource.setPoolName(properties.getName());
		}
		return dataSource;
	}
}
