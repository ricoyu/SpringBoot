package com.sexyuncle.spring.rabbitmq.message_properties;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.spring.rabbitmq.consumer.QueueingConsumer;
import com.sexyuncle.spring.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/10/30 15:25
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class RabbitmqMessageConsumer {
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		//1:创建连接工厂
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		//3:通过连接工厂创建连接对象
		Connection connection = connectionFactory.newConnection();
		//4:通过连接创建channel
		Channel channel = connection.createChannel();
		
		String exchangeName = "tuling.directchange";
		String exchangeType = "direct";
		String queueName = "tuling.directqueue";
		String routingKey = "tuling.directchange.key";
		/**
		 * 声明一个交换机
		 * exchange:  交换机的名称
		 * type:交    换机的类型 常见的有direct,fanout,topic等
		 * durable:   设置是否持久化。持久化可以将交换器存入磁盘，在服务器重启的时候不会丢失相关信息
		 * autodelete:设置是否自动删除。自动删除的前提是至少有一个队列或者交换器与这个交换器绑定，之后，所有与这个交换器绑定的队列或者交换器都与此解绑。
		 *            不能错误的理解—当与此交换器连接的客户端都断开连接时，RabbitMq会自动删除本交换器
		 * arguments: 其它一些结构化的参数，比如：alternate-exchange
		 */
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);
		
		/**
		 * 声明一个队列
		 * durable:   表示rabbitmq关闭删除队列
		 * exclusive: 队列是否是独占的, 即这个队列只能被一个消费者占有; 消息只能被一个消费者消费; 资源独占
		 * autodelete:表示没有程序和队列建立连接 那么就会自动删除队列
		 */
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);
		
		/**
		 * 创建一个消费者
		 */
		QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
		
		/**
		 * 开始消费
		 */
		channel.basicConsume(queueName,true,queueingConsumer);
		
		while (true) {
			QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
			String reserveMsg = new String(delivery.getBody());
			System.out.println("encoding:"+delivery.getProperties().getContentEncoding());
			System.out.println("company:"+delivery.getProperties().getHeaders().get("company"));
			System.out.println("correlationId:"+delivery.getProperties().getCorrelationId());
		}
	}
}
