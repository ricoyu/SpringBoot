package com.sexyuncle.springboot.bootstrap.config;

import com.loserico.common.lang.concurrent.TraceThreadPoolExecutor;
import com.loserico.web.filter.TraceFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * <p>
 * Copyright: (C), 2020/1/3 15:14
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
public class AppConfig {
	
	@Bean
	public Filter traceFilter() {
		return new TraceFilter();
	}
	
	@Bean
	public TraceThreadPoolExecutor traceThreadPoolExecutor() {
		return new TraceThreadPoolExecutor();
	}
}
