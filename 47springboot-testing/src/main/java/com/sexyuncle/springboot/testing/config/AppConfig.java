package com.sexyuncle.springboot.testing.config;

import com.loserico.orm.dao.JpaDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * <p>
 * Copyright: (C), 2019/12/27 15:33
 * <p>
 * Company: Sexy Uncle Inc.
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
public class AppConfig {
	
	@Bean
	public JpaDao jpaDao() {
		return new JpaDao();
	}
}
