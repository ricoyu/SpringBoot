package com.sexyuncle.springboot.rabbitmq.ack_nack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.springboot.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/11/4 15:00
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class AckNackRabbitmqConsumer {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		//声明交换机
		String exchangeName = "tuling.ack.direct";
		String exchangeType = "direct";
		
		/**
		 * 声明一个交换机
		 * exchange:   交换机的名称
		 * type:       交换机的类型. 常见的有direct,fanout,topic等
		 * durable:    设置是否持久化, 持久化可以将交换器存入磁盘, 在服务器重启的时候不会丢失相关信息
		 * autodelete: 没有任何队列与这个交换机绑定时自动删除交换机
		 * arguments:  其它一些结构化的参数, 比如：alternate-exchange
		 */
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);
		
		//声明队列
		String queueName = "tuling.ack.queue";
		channel.queueDeclare(queueName, true, false, false, null);
		
		//交换机绑定队列
		String routingKey = "tuling.ack.key";
		channel.queueBind(queueName, exchangeName, routingKey);
		
		/**
		 * 消费端限流 需要关闭消息自动签收
		 */
		channel.basicConsume(queueName, false, new TulingAckConsumer(channel));
	}
}
