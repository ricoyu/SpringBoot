package com.sexyuncle.springboot.druid.mybatis;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Sharding-jdbc Druid数据源 Master-Slave读写分离 Mybatis配置
 * <p>
 * Copyright: Copyright (c) 2019-02-16 21:38
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class})
@EnableTransactionManagement
@MapperScan(basePackages = "com.sexyuncle.springboot.druid.mybatis.mapper")
@EnableApolloConfig
public class ShardingJdbcDruidMyBatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShardingJdbcDruidMyBatisApplication.class, args);
	}
}
