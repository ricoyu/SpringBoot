package com.sexyuncle.spring.rabbitmq.direct_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.spring.rabbitmq.consumer.QueueingConsumer;
import com.sexyuncle.spring.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/10/30 11:57
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class DirectExchangeConsumer {
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchangeName = "tuling.directchange";
		String exchangeType = "direct";
		String queueName = "tuling.directqueue";
		/**
		 * 如果先用"tuling.directchange.key"运行过一遍
		 * 那么交换机"tuling.directchange"就和"tuling.directqueue"通过"tuling.directchange.key"这个routing_key绑定了
		 * 如果接下来我修改一个这个routing_key, 比如改成"tuling.directchange.key111", 再运行一遍这个consumer, 可以看到producer
		 * 发完消息后这个consumer还能收到消息. 
		 * 原因是:
		 * 这个"tuling.directqueue"和"tuling.directchange"之间通过两个routing_key绑定了
		 * 要先解绑一下之前的routing_key, 再次运行才收不到消息
		 */
		String routingKey = "tuling.directchange.key";
		
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
		
		/**
		 * 声明一个队列
		 * durable:   表示rabbitmq关闭删除队列
		 * exclusive: 队列是否是独占的, 即这个队列只能被一个消费者占有; 消息只能被一个消费者消费; 资源独占
		 * autodelete:表示没有程序和队列建立连接 那么就会自动删除队列
		 */
		channel.queueDeclare(queueName, true, false, false, null);
		
		/**
		 * 队列和交换机绑定
		 */
		channel.queueBind(queueName, exchangeName, routingKey);
		/**
		 * 创建一个消费者
		 */
		QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
		/**
		 * 开始消费
		 */
		channel.basicConsume(queueName, true, queueingConsumer);
		while (true) {
			QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
			String reciverMessage = new String(delivery.getBody(), "UTF-8");
			System.out.println("消费消息:-----" + reciverMessage);
		}
	}
}