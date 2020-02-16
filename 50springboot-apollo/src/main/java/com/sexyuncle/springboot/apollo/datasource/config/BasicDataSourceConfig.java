package com.sexyuncle.springboot.apollo.datasource.config;

import com.loserico.common.lang.utils.BeanUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Copyright: (C), 2020/1/16 16:51
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
public class BasicDataSourceConfig {
	
	
	
	@Bean
	public BasicDataSource dataSourceDefault(BasicDataSourceProperties properties) {
		BasicDataSource dataSource = new BasicDataSource();
		BeanUtils.copyProperties(properties, dataSource);
		properties.setDataSourceInstance(dataSource);
		return dataSource;
	}
}
