package com.sexyuncle.spring.rabbitmq.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.spring.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/11/5 11:30
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class DlxRabbitmqProducer {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		//消息十秒没有被消费，那么就会转到死信队列上
		AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
				.deliveryMode(2)
				.expiration("10000")
				.build();
		
		//声明正常的队列
		String nomalExchangeName = "tuling.normaldlx.exchange";
		String routingKey = "tuling.dlx.key1";
		String message = "我是测试的死信消息";
		
		for (int i = 0; i < 100; i++) {
			channel.basicPublish(nomalExchangeName, routingKey, basicProperties, (message + i).getBytes(StandardCharsets.UTF_8));
			System.out.println("发送: " + (message + i));
		}
	}
}
