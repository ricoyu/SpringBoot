package com.sexyuncle.spring.rabbitmq.return_listener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.spring.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/11/5 9:39
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class RetruningListenerConsumer {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		//声明交换机
		String exchangeName = "tuling.retrun.direct";
		String exchangeType = "direct";
		//声明队列
		String queueName = "t04.retrunlistener.queue";
		//声明一个绑定
		String routingKey = "tuling.retrun.key.ok";
		
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);
		
		channel.basicConsume(queueName, true, new ListenerConsumer(channel));
	}
}
