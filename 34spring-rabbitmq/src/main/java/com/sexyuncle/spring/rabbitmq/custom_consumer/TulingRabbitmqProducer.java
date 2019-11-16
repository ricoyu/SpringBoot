package com.sexyuncle.spring.rabbitmq.custom_consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.spring.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/11/4 11:05
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class TulingRabbitmqProducer {
	
	/**
	 * 要先运行Consumer, 因为exchange是由consumer创建的, 不然这边发的时候exchange还不存在就会报错
	 *
	 * @param args
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		//定义交换机的名称
		String exchangeName = "tuling.customconsumer.direct";
		String routingKey = "tuling.customconsumer.key";
		String msgBody = "你好tuling";
		
		for (int i = 0; i < 5; i++) {
			channel.basicPublish(exchangeName, routingKey, null, (msgBody + i).getBytes(StandardCharsets.UTF_8));
		}
		System.out.println("done");
	}
}
