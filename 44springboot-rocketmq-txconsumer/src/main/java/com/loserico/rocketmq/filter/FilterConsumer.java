package com.loserico.rocketmq.filter;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>
 * Copyright: (C), 2019/12/15 19:57
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class FilterConsumer {
	
	public static void main(String[] args) throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TopicFilterGroup");
		consumer.setNamesrvAddr("192.168.2.101:9876;192.168.2.102:9876");
		/**
		 * 订阅主题
		 * 一种资源去换取另外一种资源
		 */
		consumer.subscribe("TopicFilter", MessageSelector.bySql("a > 3 and b = 'zaizai'"));
		//consumer.subscribe("TopicFilter", MessageSelector.bySql("a between 0 and 3 and b = 'zaizai'"));
		/**
		 * 注册监听器，监听主题消息
		 */
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				for (MessageExt msg : msgs) {
					System.out.println("consumeThread=" + Thread.currentThread().getName()
							+ ", queueId=" + msg.getQueueId() + ", content:"
							+ new String(msg.getBody(), StandardCharsets.UTF_8));
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		
		consumer.start();
		System.out.printf("Filter Consumer Started.%n");
	}
}
