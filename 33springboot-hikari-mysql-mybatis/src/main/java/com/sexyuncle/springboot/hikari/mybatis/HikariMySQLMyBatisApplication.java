package com.sexyuncle.springboot.hikari.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hikari基于MySQL的优化配置示例
 * <p>
 * Copyright: Copyright (c) 2019-02-16 21:38
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@SpringBootApplication
public class HikariMySQLMyBatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(HikariMySQLMyBatisApplication.class, args);
	}
}
