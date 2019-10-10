package com.sexyuncle.springboot.scp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

import com.loserico.web.advice.RestExceptionAdvice;
import com.loserico.web.context.support.CustomConversionServiceFactoryBean;

/**
 * Web 层相关配置
 * <p>
 * Copyright: Copyright (c) 2018-05-22 14:46
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@Configuration
public class WebConfig {

	/**
	 * 异常处理
	 * 
	 * @return
	 */
	@Bean
	public RestExceptionAdvice restExceptionAdvice() {
		return new RestExceptionAdvice();
	}

	@Bean(name = "conversionService")
	public ConversionService conversionService() {
		CustomConversionServiceFactoryBean conversionServiceFactoryBean = new CustomConversionServiceFactoryBean();
		conversionServiceFactoryBean.getProperties().add("code");
		conversionServiceFactoryBean.afterPropertiesSet();
		return conversionServiceFactoryBean.getObject();
	}
}
