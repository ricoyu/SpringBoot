package com.sexyuncle.springboot.bootstrap.config;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter
 * 在工程中开启监控功能后，可以在工程应用运行过程中，通过Druid数据源连接池自带SQL监控提供的多维度数据，分析出业务SQL执行的情况，从而可以调整和优化代码以及SQL，方便业务开发同事调优数据库的访问性能。
 * 
 * 要达到开启SQL监控的效果，还需在Spring Boot工程中还实现Druid数据源连接池的Serlvet以及Filter，其Bean的初始化代码如下
 * 
 * <p>
 * Copyright: Copyright (c) 2018-04-17 11:15
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 * 
 */
@Configuration
public class DBConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(DBConfig.class);

	@Bean
	public DruidDataSource dataSource() throws SQLException {
		DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
		/*
		 * 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
		 * 监控统计用的filter:   stat
		 * 日志用的filter:     log4j
		 * 防御sql注入的filter: wall
		 * filters: stat, wall, log4j
		 * 
		 * SQL合并配置
		 * 当你程序中存在没有参数化的sql执行时，sql统计的效果会不好。比如：
		 * select * from t where id = 1
		 * select * from t where id = 2
		 * select * from t where id = 3
		 * 在统计中，显示为3条sql，这不是我们希望要的效果。StatFilter提供合并的功能，能够将这3个SQL合并为如下的SQL
		 * select * from t where id = ?
		 * 
		 * filters不能再yml中配置
		 * @on
		 */
		dataSource.setFilters("stat,mergeStat,wall,slf4jlog");
		
		return dataSource;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean druidServlet() {
		logger.info("init Druid Servlet Configuration ");
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		// IP白名单 (没有配置或者为空，则允许所有访问)
		servletRegistrationBean.addInitParameter("allow", "");
		// IP黑名单(共同存在时，deny优先于allow)
		servletRegistrationBean.addInitParameter("deny", "192.168.1.116");
		//控制台管理用户
		servletRegistrationBean.addInitParameter("loginUsername", "admin");
		servletRegistrationBean.addInitParameter("loginPassword", "test123456");
		//是否能够重置数据 禁用HTML页面上的“Reset All”功能
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}
}
