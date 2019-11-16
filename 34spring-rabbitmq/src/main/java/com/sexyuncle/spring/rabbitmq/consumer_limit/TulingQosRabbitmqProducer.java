package com.sexyuncle.spring.rabbitmq.consumer_limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.spring.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/11/4 21:20
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class TulingQosRabbitmqProducer {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		//定义交换机的名称
		String exchangeName = "tuling.qos.direct";
		String routingKey = "tuling.qos.key";
		String msgBody = "你好tuling";
		
		for (int i = 0; i < 100; i++) {
			channel.basicPublish(exchangeName, routingKey, null, (msgBody + i).getBytes(StandardCharsets.UTF_8));
		}
	}
}
