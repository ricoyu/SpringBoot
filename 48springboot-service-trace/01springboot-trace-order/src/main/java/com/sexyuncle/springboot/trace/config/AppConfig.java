package com.sexyuncle.springboot.trace.config;

import com.loserico.common.spring.interceptor.TraceIdHttpRequestInterceptor;
import com.loserico.web.filter.TraceFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.servlet.Filter;
import java.util.Collections;

/**
 * <p>
 * Copyright: (C), 2020/1/3 14:48
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
	public RestTemplate restTemplate() {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setReadTimeout(2000);
		requestFactory.setConnectTimeout(2000);
		
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.setInterceptors(Collections.singletonList(new TraceIdHttpRequestInterceptor()));
		return restTemplate;
	}
	
	@Bean
	public Filter traceidFilter() {
		return new TraceFilter();
	}
}
