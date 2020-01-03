package com.sexyuncle.springboot.config;

import com.sexyuncle.springboot.filter.PathVariableFilter;
import com.sexyuncle.springboot.filter.RequestResponseLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MvcConfig {

	@Bean
	public FilterRegistrationBean<RequestResponseLoggingFilter> loggingFilter() {
		FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new RequestResponseLoggingFilter());
		registrationBean.addUrlPatterns("/users/*");
		return registrationBean;
	}
	
	@Bean
	public FilterRegistrationBean<PathVariableFilter> pathVariableFilter() {
		FilterRegistrationBean<PathVariableFilter> pathVariableFilter = new FilterRegistrationBean<>();
		pathVariableFilter.setFilter(new PathVariableFilter());
		pathVariableFilter.addUrlPatterns("/*");
		return pathVariableFilter;
	}
}
