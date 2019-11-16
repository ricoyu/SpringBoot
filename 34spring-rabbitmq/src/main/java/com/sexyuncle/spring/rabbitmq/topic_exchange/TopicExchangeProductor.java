package com.sexyuncle.spring.rabbitmq.topic_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.spring.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/10/30 14:34
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class TopicExchangeProductor {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		//创建连接
		Connection connection = connectionFactory.newConnection();
		//创建channel
		Channel channel = connection.createChannel();
		
		String exchangeName = "tuling.topicexchange";
		/**
		 * Producer发送的时候指定具体的routing_key
		 */
		String routingKey1 = "tuling.key1";
		String routingKey2 = "tuling.key2";
		String routingKey3 = "tuling.key.key3";
		
		channel.basicPublish(exchangeName, routingKey1, null, "我是第一条消息".getBytes(StandardCharsets.UTF_8));
		channel.basicPublish(exchangeName, routingKey2, null, "我是第二条消息".getBytes(StandardCharsets.UTF_8));
		channel.basicPublish(exchangeName, routingKey3, null, "我是第三条消息".getBytes(StandardCharsets.UTF_8));
	}
}
