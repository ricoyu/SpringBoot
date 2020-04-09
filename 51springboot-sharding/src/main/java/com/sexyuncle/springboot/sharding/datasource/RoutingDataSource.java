package com.sexyuncle.springboot.sharding.datasource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class RoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDataSourceKey();
	}

	@Override
	public Connection getConnection() throws SQLException {
		DataSource dataSource = determineTargetDataSource();
		return DataSourceUtils.getConnection(dataSource);
	}

}