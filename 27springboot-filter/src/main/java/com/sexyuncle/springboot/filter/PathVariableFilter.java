package com.sexyuncle.springboot.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * Copyright: (C), 2019/12/31 16:17
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Slf4j
public class PathVariableFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
	                     FilterChain chain) throws IOException, ServletException {
		Map<String, Object> variables = (Map<String, Object>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		log.info("{}", variables);
		chain.doFilter(request, response);
	}
}
