package com.sexyuncle.springboot.rabbitmq.utils;

import com.rabbitmq.client.ConnectionFactory;

/**
 * <p>
 * Copyright: (C), 2019/10/30 10:56
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public final class ConnectionFactoryUtils {
	
	public static ConnectionFactory connectionFactory() {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.2.101");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("tuling");
		connectionFactory.setUsername("rico");
		connectionFactory.setPassword("123456");
		connectionFactory.setConnectionTimeout(10000);
		return connectionFactory;
	}
}
