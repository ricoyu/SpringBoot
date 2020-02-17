package com.sexyuncle.springboot.sharding.datasource;


import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 多数据源类
 * <p>
 * Copyright: (C), 2020/2/14 21:02
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class RoutingDataSource extends AbstractRoutingDataSource {
	
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceHolder.getDataSourceKey();
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		DataSource dataSource = determineTargetDataSource();
		return DataSourceUtils.getConnection(dataSource);
	}
}
