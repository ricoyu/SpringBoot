package com.sexyuncle.spring.rabbitmq.confirm_listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.spring.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/11/4 20:39
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class ConfirmRabbitmqProducer {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		/**
		 * 设置消息投递模式(确认模式)
		 */
		channel.confirmSelect();
		
		//准备发送消息
		String exchangeName = "tuling.confirm.topicexchange";
		String routingKey = "tuling.confirm.key";
		
		//设置消息属性
		Map<String, Object> tulingInfo = new HashMap<>();
		tulingInfo.put("company", "tuling");
		tulingInfo.put("location", "长沙");
		
		AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
				.deliveryMode(2)
				.correlationId(UUID.randomUUID().toString())
				.timestamp(new Date())
				.headers(tulingInfo)
				.build();
		
		String msgContext = "你好 图灵....";
		/**
		 * 消息确认监听
		 */
		channel.addConfirmListener(new TulingConfirmListener());
		channel.basicPublish(exchangeName, routingKey, basicProperties, msgContext.getBytes(StandardCharsets.UTF_8));
		/**
		 * 注意:在这里千万不能调用channel.close不然 消费就不能接受确认了
		 */
	}
}
