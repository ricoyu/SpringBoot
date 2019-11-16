package com.sexyuncle.spring.rabbitmq.return_listener;

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
 * Copyright: (C), 2019/11/5 9:33
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class ReturingListenerProducer {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		//准备发送消息
		String exchangeName = "tuling.retrun.direct";
		String okRoutingKey = "tuling.retrun.key.ok";
		String errorRoutingKey = "tuling.retrun.key.error";
		
		/**
		 * 设置监听不可达消息
		 */
		channel.addReturnListener(new TulingRetrunListener());
		
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
		
		String msgContext = "你好 图灵...." + System.currentTimeMillis();
		
		/**
		 * 发送消息
		 * mandatory:false 不可达消息会被mq broker给删除掉
		 *           true  mq会调用我们的 retrunListener 来告诉我们业务系统说该消息不能成功发送.
		 */
		channel.basicPublish(exchangeName, okRoutingKey, true, basicProperties, msgContext.getBytes(StandardCharsets.UTF_8));
		
		String errorMsg1 = "你好 图灵 mandotory为false...." + System.currentTimeMillis();
		//错误发送   mandotory为false
		channel.basicPublish(exchangeName, errorRoutingKey, false, basicProperties, errorMsg1.getBytes(StandardCharsets.UTF_8));
		
		String errorMsg2 = "你好 图灵 mandotory为true...."+System.currentTimeMillis();
		channel.basicPublish(exchangeName, errorRoutingKey, true, basicProperties, errorMsg1.getBytes(StandardCharsets.UTF_8));
		System.out.println("done");
	}
}
