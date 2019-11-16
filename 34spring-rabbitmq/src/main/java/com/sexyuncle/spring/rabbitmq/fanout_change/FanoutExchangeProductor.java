package com.sexyuncle.spring.rabbitmq.fanout_change;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.spring.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/10/30 15:08
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class FanoutExchangeProductor {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		
		//创建连接
		Connection connection = connectionFactory.newConnection();
		
		//创建channel
		Channel channel = connection.createChannel();
		
		//定义交换机名称
		String exchangeName = "tuling.fanoutexchange";
		
		//定义routingKey
		String routingKey = "";
		
		//消息体内容
		String messageBody = "hello tuling ";
		channel.basicPublish(exchangeName,"123",null,"我是第一条消息".getBytes());
		channel.basicPublish(exchangeName,"456",null,"我是第二条消息".getBytes());
		channel.basicPublish(exchangeName,"789",null,"我是第三条消息".getBytes());
	}
}
