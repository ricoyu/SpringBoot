package com.sexyuncle.springboot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * Copyright: (C), 2021-01-14 13:58
 * <p>
 * <p>
 * Company: Information & Data Security Solutions Co., Ltd.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Bean
	public Filter filter() {
		return new Filter() {
			@Override
			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
				HttpServletRequest httpServletRequest = (HttpServletRequest)request;
				String url = httpServletRequest.getRequestURL().toString();
				String uri = httpServletRequest.getRequestURI();
				if (uri.contains("/swagger-ui.html")) {
					HttpServletResponse httpServletResponse = (HttpServletResponse)response;
					httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
					return;
				}
				chain.doFilter(request, response);
			}
		};
	}
	
}
