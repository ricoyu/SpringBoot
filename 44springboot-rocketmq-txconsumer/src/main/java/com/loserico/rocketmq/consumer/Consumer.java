package com.loserico.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

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
	
	public static void main(String[] args) throws MQClientException {
		//group要跟producer在不在同一个group里面, 只要Topic一样就可以消费到
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("tl_student_group");
		consumer.setNamesrvAddr("192.168.2.101:9876;192.168.2.102:9876");
		
		// Subscribe one more more topics to consume.
		consumer.subscribe("TopicTestTag", "TagB");
		//consumer.subscribe("TopicStudent", "*");
		// Register callback to execute on arrival of messages fetched from brokers.
		MessageListenerConcurrently messageListenerConcurrently = (msgs, context) -> {
			for (MessageExt msg : msgs) {
				System.out.println(new String(msg.getBody()));
			}
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		};
		consumer.registerMessageListener(messageListenerConcurrently);
		consumer.start();
		System.out.printf("Consumer Started.%n");
	}
}
