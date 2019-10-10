package com.sexyuncle.springboot.hikari.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.loserico.commons.utils.EnumUtils;
import com.sexyuncle.springboot.hikari.context.CompanyContextHolder;
import com.sexyuncle.springboot.hikari.enums.Company;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(1)
public class DynamicDataSourceFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Company company = (Company)EnumUtils.lookupEnum(Company.class, httpServletRequest.getParameter("company"));
		if (company != null) {
			log.info("Auto switch to {}'s database", company);
			CompanyContextHolder.setCompany(company);
		}
		chain.doFilter(request, response);
	}

}
