package com.sexyuncle.spring.rabbitmq.direct_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.spring.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/10/30 11:01
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class DirectExchangeProducer {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		//创建连接
		Connection connection = connectionFactory.newConnection();
		//创建channel
		Channel channel = connection.createChannel();
		//定义交换机名称
		String exchangeName = "tuling.directchange";
		//定义routingKey
		String routingKey = "tuling.directchange.key";
		//消息体内容
		String messageBody = "hello tuling ";
		channel.basicPublish(exchangeName, routingKey, null, messageBody.getBytes(StandardCharsets.UTF_8));
	}
}
