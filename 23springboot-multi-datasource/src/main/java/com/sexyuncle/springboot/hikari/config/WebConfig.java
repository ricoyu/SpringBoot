package com.sexyuncle.springboot.hikari.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletRequestListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loserico.commons.concurrent.ThreadLocalCleanupListener;
import com.loserico.web.advice.GlobalBindingAdivce;
import com.loserico.web.advice.RestExceptionAdvice;
import com.loserico.web.converter.GenericEnumConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * 全局异常处理
	 * 
	 * @return
	 */
	@Bean
	public RestExceptionAdvice restExceptionAdvice() {
		return new RestExceptionAdvice();
	}

	@Bean
	public GlobalBindingAdivce globalBindingAdivce() {
		return new GlobalBindingAdivce();
	}

	@Bean
	public ServletListenerRegistrationBean<ServletRequestListener> listenerRegistrationBean() {
		ServletListenerRegistrationBean<ServletRequestListener> bean = new ServletListenerRegistrationBean<>();
		bean.setListener(new ThreadLocalCleanupListener());
		return bean;
	}

	/**
	 * 支持Enum类型参数绑定，可以按名字，也可以按制定的属性
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		Set<String> properties = new HashSet<>();
		properties.add("code");
		properties.add("desc");
		registry.addConverter(new GenericEnumConverter(properties));
		WebMvcConfigurer.super.addFormatters(registry);
	}

	/**
	 * MVC层使用的ObjectMapper 需要像这样配置，
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
		converters.add(new ByteArrayHttpMessageConverter());
	}

}
