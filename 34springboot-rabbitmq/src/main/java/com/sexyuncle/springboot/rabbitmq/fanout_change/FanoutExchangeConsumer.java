package com.sexyuncle.springboot.rabbitmq.fanout_change;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.sexyuncle.springboot.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/10/30 15:09
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class FanoutExchangeConsumer {
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		
		//创建连接
		Connection connection = connectionFactory.newConnection();
		//创建channel
		Channel channel = connection.createChannel();
		
		//声明交换机
		String exchangeName = "tuling.fanoutexchange";
		String exchangeType = "fanout";
		
		/**
		 * 声明一个队列
		 * durable:   表示rabbitmq关闭删除队列
		 * autodelete:表示没有程序和队列建立连接 那么就会自动删除队列
		 */
		channel.exchangeDeclare(exchangeName, exchangeType, true, true, null);
		
		//声明队列
		String queueName = "tuling.fanout.queue";
		channel.queueDeclare(queueName, true, false, false, null);
		
		//声明绑定关系
		String bingdingStr = "jjsadf";
		channel.queueBind(queueName, exchangeName, bingdingStr);
		
		//声明一个消费者
		QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, queueingConsumer);
		
		while (true) {
			QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
			System.out.println("接受到消息:" + new String(delivery.getBody(), "UTF-8"));
		}
	}
}
