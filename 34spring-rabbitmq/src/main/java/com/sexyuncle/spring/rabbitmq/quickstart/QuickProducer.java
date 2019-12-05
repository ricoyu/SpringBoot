package com.sexyuncle.spring.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.spring.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/10/30 11:09
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class QuickProducer {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		//创建连接
		Connection connection = connectionFactory.newConnection();
		
		//创建我们的channle
		Channel channel = connection.createChannel();
		for (int i = 0; i < 5; i++) {
			String message = "helo--" + i;
			/*
			 * 消息都是直接发送到交换机上的
			 * 这里交换机是空, 表示发送到默认交换机(tuling (AMQP default)那个)
			 *
			 * The default exchange is implicitly bound to every queue, with a routing key equal to the queue name. 
			 * It is not possible to explicitly bind to, or unbind from the default exchange. It also cannot be deleted.
			 * 
			 * 第二个routingKey必须和消费者监听的Queue名字一模一样(参考上面的英文解释)
			 * 
			 * PS: 不建议这么写
			 */
			channel.basicPublish("", "tuling-queue-01", null, message.getBytes());
		}
		//关闭连接
		channel.close();
		connection.close();
		System.out.println("done");
	}
}
