package com.loserico.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * Copyright: (C), 2019/12/12 21:22
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class Consumer {
	
	private static ThreadPoolExecutor executor =
			new ThreadPoolExecutor(6, 500, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(200));
	
	public static void main(String[] args) throws MQClientException {
		//group要跟producer在不在同一个group里面, 只要Topic一样就可以消费到
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("tl_student_group");
		consumer.setNamesrvAddr("192.168.2.101:9876;192.168.2.102:9876");
		consumer.setPullBatchSize(32);
		consumer.setConsumeMessageBatchMaxSize(1);
		consumer.setConsumeThreadMin(20);
		consumer.setConsumeThreadMax(64);
		// Subscribe one more more topics to consume.
		consumer.subscribe("TopicStudent", "TagStudent");
		//consumer.subscribe("TopicStudent", "*");
		// Register callback to execute on arrival of messages fetched from brokers.
		AtomicLong count = new AtomicLong(1);
		MessageListenerConcurrently messageListenerConcurrently = (msgs, context) -> {
			executor.execute(() -> {
				try {
					TimeUnit.SECONDS.sleep(4);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("一次拉取到: " + msgs.size() + " 条消息");
				for (MessageExt msg : msgs) {
					System.out.println(new String(msg.getBody()));
				}
			});
			System.out.println("=======================消费成功: " + count.getAndIncrement() + "=======================");
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		};
		
		consumer.registerMessageListener(messageListenerConcurrently);
		consumer.start();
		System.out.printf("Consumer Started.%n");
	}
}
