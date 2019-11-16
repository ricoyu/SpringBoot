package com.sexyuncle.spring.rabbitmq.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sexyuncle.spring.rabbitmq.utils.ConnectionFactoryUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * Copyright: (C), 2019/11/5 11:17
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class DlxRabbitmqConsumer {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = ConnectionFactoryUtils.connectionFactory();
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		/**
		 * 声明两套队列
		 * 正常的消息没有被处理的话就会转到死信队列上面
		 */
		//声明正常的队列
		String normalExchangeName = "tuling.normaldlx.exchange";
		String exchangeType = "topic";
		String normalqueueName = "tuling.normaldlx.queue";
		String routingKey = "tuling.dlx.#";
		
		//申明死信队列
		String dlxExhcangeName = "tuling.dlx.exchange";
		String dlxQueueName = "tuling.dlx.queue";
		
		channel.exchangeDeclare(normalExchangeName, exchangeType, true, false, null);
		Map<String, Object> queueArgs = new HashMap<>();
		//正常队列上绑定死信队列
		queueArgs.put("x-dead-letter-exchange", dlxExhcangeName);
		/**
		 * 如果消费端关掉后, 生产端再发送100条数据, 会有96条直接进到死信队列, 4条留在tuling.normaldlx.queue队列里面
		 * 然后再十秒钟以后, 这4条也进入死信队列
		 *
		 * 解释: 正常队列的长度是4, 所以超过4条的消息都会直接进到死信队列
		 * 消息生产者配置消息过期时间是10秒(expiration("10000")), 所以4条留在正常队列的消息在10秒后没有被消费则进入死信队列
		 * 
		 * 实际作用: 可以作为一个延时队列来使用. 比如订单超时未支付的场景
		 */
		//队列的最大长度
		queueArgs.put("x-max-length", 4);
		channel.queueDeclare(normalqueueName, true, false, false, queueArgs);
		channel.queueBind(normalqueueName, normalExchangeName, routingKey);
		
		//声明死信队列
		channel.exchangeDeclare(dlxExhcangeName, exchangeType, true, false, null);
		channel.queueDeclare(dlxQueueName, true, false, false, null);
		channel.queueBind(dlxQueueName, dlxExhcangeName, "#");
		
		channel.basicConsume(normalqueueName, false, new DlxConsumer(channel));
		System.out.println("done");
	}
}
