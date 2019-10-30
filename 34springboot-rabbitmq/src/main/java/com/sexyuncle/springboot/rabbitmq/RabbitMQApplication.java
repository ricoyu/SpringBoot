package com.sexyuncle.springboot.rabbitmq;

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
@SpringBootApplication
@EnableTransactionManagement
public class RabbitMQApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitMQApplication.class, args);
	}
}
