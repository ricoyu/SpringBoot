package com.sexyuncle.springboot.rabbitmq.utils;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;

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
	
	public static ConnectionFactory connectionFactory(String host, String virtualHost, String username, String password) {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(host);
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost(virtualHost);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		return connectionFactory;
	}
}
