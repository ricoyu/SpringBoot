package com.sexyuncle.springboot.sharding.config;

import com.sexyuncle.springboot.sharding.datasource.RoutingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Copyright: (C), 2020/2/17 14:27
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
public class DynamicDataSourceConfig {
	
	@Autowired
	private DataSource dataSource00;
	
	@Autowired
	private DataSource dataSource01;
	
	@Autowired
	private DataSource dataSource02;
	
	@Bean
	public DataSource dataSource() {
		RoutingDataSource dataSource = new RoutingDataSource();
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put("dataSource00", dataSource00);
		targetDataSources.put("dataSource01", dataSource01);
		targetDataSources.put("dataSource02", dataSource02);
		dataSource.setTargetDataSources(targetDataSources);
		dataSource.setDefaultTargetDataSource(dataSource00);
		return dataSource;
	}
}
