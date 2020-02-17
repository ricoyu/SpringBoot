package com.sexyuncle.springboot.sharding.config;

import com.loserico.orm.dao.JpaDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
public class DataSourceConfig {
	
	@Bean
	public JpaDao jpaDao() {
		return new JpaDao();
	}
}
