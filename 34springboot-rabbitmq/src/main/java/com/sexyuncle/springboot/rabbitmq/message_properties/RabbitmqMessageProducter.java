package com.sexyuncle.springboot.rabbitmq.message_properties;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.springboot.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/10/30 15:18
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class RabbitmqMessageProducter {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//1:创建连接工厂
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		//3:通过连接工厂创建连接对象
		Connection connection = connectionFactory.newConnection();
		//4:通过连接创建channel
		Channel channel = connection.createChannel();
		
		Map<String, Object> headsMap = new HashMap<>();
		headsMap.put("company", "tuling");
		headsMap.put("name", "smlz");
		
		AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
				.deliveryMode(2) //2标识持久化消息  1标识 服务重启后 消息不会被持久化
				.expiration("10000")//消息过期 10s, 如果消息过十秒还没被消费掉, RabbitMQ就会把消息删掉
				.contentEncoding("UTF-8")
				.correlationId(UUID.randomUUID().toString()) //这个属性是做可靠性投递的核心保障
				.headers(headsMap)
				.build();
		
		//5:通过channel发送消息
		for (int i = 0; i < 5; i++) {
			String message = "hello--" + i;
			/**
			 * 老师以前讲过说我们的消息会发送的exchange上，
			 * 但是在这里我们没有指定交换机?那我们的消息发送到哪里了？？？？
			 * The default exchange is implicitly bound to every queue, with a routing key equal to the queue name.
			 * It is not possible to explicitly bind to, or unbind from the default exchange. It also cannot be deleted.
			 * 说明:假如我们消息发送的时候没有指定具体的交换机的话，那么就会发送到rabbimtq指定默认的交换机上，
			 * 那么该交换机就会去根据routing_key 查找对应的queueName 然后发送的该队列上.
			 */
			channel.basicPublish("tuling.directchange", "tuling.directchange.key", basicProperties, message.getBytes("utf-8"));
		}
		
		//6:关闭连接
		channel.close();
		connection.close();
	}
}
