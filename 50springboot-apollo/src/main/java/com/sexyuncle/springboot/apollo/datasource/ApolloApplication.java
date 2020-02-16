package com.sexyuncle.springboot.apollo.datasource;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.loserico.common.lang.utils.IOUtils;
import com.loserico.orm.dao.EntityOperations;
import com.sexyuncle.springboot.apollo.datasource.config.DataSourceProperties;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据源配置放到阿波罗, 在阿波罗修改数据源配置实时应用更改
 * <p>
 * Copyright: Copyright (c) 2019-02-16 21:38
 * <p>
 * Company: DataSense
 * <p>
 *
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
@EnableApolloConfig({"application", "db", "datasource", "rocketmq"})
@SpringBootApplication
public class ApolloApplication implements CommandLineRunner {
	
	@Value("${message:你好三少爷}")
	private String message;
	
	@Value("${rocketmq.namesrvAddr}")
	private String namesrvAddr;
	
	@Resource
	private BasicDataSource basicDataSource;
	
	@Resource
	private DataSourceProperties datasourceProperties;
	
	@Resource
	private EntityOperations entityOperations;
	
	public static void main(String[] args) {
		SpringApplication.run(ApolloApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		IOUtils.readCommandLine((s) -> {
			//System.out.println(entityOperations.get(User.class, 1).getName() + " " + message);
			//System.out.println(datasourceProperties);
			System.out.println(execute());
		});
	}
	
	private String execute() {
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("select name from user where id=1");
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
