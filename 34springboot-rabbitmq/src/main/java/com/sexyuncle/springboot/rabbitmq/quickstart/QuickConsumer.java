package com.sexyuncle.springboot.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.sexyuncle.springboot.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/10/30 11:27
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class QuickConsumer {
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		//声明队列名称
		String queueName = "tuling-queue-01";
		/*
		 * durable:   队列持久化
		 * exclusive: 队列是否是独占的, 即这个队列只能被一个消费者占有; 消息只能被一个消费者消费; 资源独占
		 * autoDelete:队列是否自动删除?
		 */
		channel.queueDeclare(queueName, true, false, false, null);
		
		//创建我们的消费者
		QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
		/*
		 * autoAck: 是否自动签收
		 * 自动签收是可靠性消息投递的核心保障
		 */
		channel.basicConsume(queueName, true, queueingConsumer);
		
		while (true) {
			QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
			System.out.println("消费消息:" + new String(delivery.getBody(), "UTF-8"));
		}
	}
}
