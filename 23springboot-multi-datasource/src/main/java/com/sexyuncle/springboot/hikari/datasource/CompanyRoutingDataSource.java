package com.sexyuncle.springboot.hikari.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.sexyuncle.springboot.hikari.context.CompanyContextHolder;

public class CompanyRoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return CompanyContextHolder.getCompany();
	}

}
