package com.sexyuncle.spring.rabbitmq.custom_consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.spring.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/11/4 11:19
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class TulingRabbitmqConsumer {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		//声明交换机
		String exchangeName = "tuling.customconsumer.direct";
		String exchangeType = "direct";
		
		/**
		 * 声明一个交换机
		 * exchange:   交换机的名称
		 * type:       交换机的类型. 常见的有direct,fanout,topic等
		 * durable:    设置是否持久化, 持久化可以将交换器存入磁盘, 在服务器重启的时候不会丢失相关信息
		 * autodelete: 设置是否自动删除, 自动删除的前提是至少有一个队列或者交换器与这个交换机绑定, 之后，所有与这个交换机绑定的队列或者交换机都与此解绑。
		 *             不能错误的理解—当与此交换器连接的客户端都断开连接时，RabbitMq会自动删除本交换器
		 *             (没有任何队列与这个交换机绑定时自动删除交换机)
		 * arguments:  其它一些结构化的参数, 比如：alternate-exchange
		 */
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);
		
		//声明队列
		String queueName = "tuling.customconsumer.queue";
		
		/**
		 * 声明一个队列
		 * durable:   表示rabbitmq关闭删除队列
		 * exclusive: 队列是否是独占的, 即这个队列只能被一个消费者占有; 消息只能被一个消费者消费; 资源独占
		 * autodelete:表示没有程序和队列建立连接 那么就会自动删除队列
		 */
		channel.queueDeclare(queueName, true, false, false, null);
		
		//交换机绑定队列
		String routingKey = "tuling.customconsumer.key";
		channel.queueBind(queueName, exchangeName, routingKey);
		
		//这个API是不自动ACK的
		channel.basicConsume(queueName, new TulingConsumer(channel));
		System.out.println("done");
	}
}
