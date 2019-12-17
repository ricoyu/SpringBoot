package com.sexyuncle.springboot.interceptor.config;

import com.sexyuncle.springboot.interceptor.handlerinterceptor.LoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * Copyright: (C), 2019/12/17 11:05
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	/**
	 * To add our interceptors into Spring configuration, we need to override addInterceptors() method inside WebConfig
	 * class that implements WebMvcConfigurer:
	 *
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor());
	}
}
