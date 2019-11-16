package com.sexyuncle.spring.rabbitmq.ack_nack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.spring.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/11/4 15:05
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class AckNackRabbitmqProducer {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		//定义交换机的名称
		String exchangeName = "tuling.ack.direct";
		String routingKey = "tuling.ack.key";
		String msgBody = "你好tuling";
		
		for (int i = 0; i < 10; i++) {
			Map<String, Object> infoMap = new HashMap<>();
			infoMap.put("marker", i);
			
			AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
					.deliveryMode(2) //消息持久化
					.contentEncoding("UTF-8")
					.correlationId(UUID.randomUUID().toString())
					.headers(infoMap)
					.build();
			channel.basicPublish(exchangeName, routingKey, basicProperties, (msgBody + i).getBytes(StandardCharsets.UTF_8));
			System.out.println("发送消息: " + i);
		}
		System.out.println("done");
	}
}
