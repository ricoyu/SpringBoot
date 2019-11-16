package com.sexyuncle.spring.rabbitmq.consumer_limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.spring.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/11/4 21:15
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class TulingQosRabbtimqConsumer {
	
	/**
	 * 消费端手动签收, 并且一次获取一条消息, 处理完一条再拉取一条
	 * 防止消息生产端生产大量消息到Broker, 导致消费端被压垮
	 *
	 * @param args
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		//声明交换机
		String exchangeName = "tuling.qos.direct";
		String exchangeType = "direct";
		
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);
		
		//声明队列
		String queueName = "tuling.qos.queue";
		channel.queueDeclare(queueName, true, false, false, null);
		
		//交换机绑定队列
		String routingKey = "tuling.qos.key";
		channel.queueBind(queueName, exchangeName, routingKey);
		
		/**
		 * 限流设置:  prefetchSize：每条消息大小的设置
		 * prefetchCount:标识每次推送多少条消息 一般是一条
		 * global:false标识channel级别的  true:标识Queue级别的, 目前channel级别的还未实现, 只有queue级别的
		 */
		channel.basicQos(0, 1, false);
		
		/**
		 * 消费端限流 需要关闭消息自动签收
		 */
		channel.basicConsume(queueName, false, new TulingQosConsumer(channel));
	}
}
