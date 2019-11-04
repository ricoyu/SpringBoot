package com.sexyuncle.springboot.rabbitmq.topic_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.sexyuncle.springboot.rabbitmq.utils.ConnectionFactoryUtils;
import org.springframework.amqp.rabbit.listener.QueuesNotAvailableException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/10/30 14:36
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class TopicExchangeConsumer {
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		ConnectionFactory connectionFactory =
				ConnectionFactoryUtils.connectionFactory();
		//创建连接
		Connection connection = connectionFactory.newConnection();
		//创建channel
		Channel channel = connection.createChannel();
		
		//声明交换机
		String exchangeName = "tuling.topicexchange";
		String exchangeType = "topic";
		
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
		channel.exchangeDeclare(exchangeName, exchangeType, true, true, null);
		
		//声明队列
		String queueName = "tuling.topic.queue";
		/**
		 * 声明一个队列
		 * durable:   表示rabbitmq关闭删除队列
		 * exclusive: 队列是否是独占的, 即这个队列只能被一个消费者占有; 消息只能被一个消费者消费; 资源独占
		 * autodelete:表示没有程序和队列建立连接 那么就会自动删除队列
		 */
		channel.queueDeclare(queueName, true, false, false, null);
		
		/**
		 * 声明绑定关系
		 * Queue和Exchange绑定的时候可以指定通配符形式的routing_key
		 */
		//String routingKey = "tuling.#";
		String routingKey = "tuling.*";
		channel.queueBind(queueName, exchangeName, routingKey);
		
		//声明一个消费者
		QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, queueingConsumer);
		
		while (true) {
			QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
			System.out.println("接受到消息:" + new String(delivery.getBody()));
		}
	}
}
