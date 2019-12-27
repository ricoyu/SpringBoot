package com.loserico.rocketmq.broadcast;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * <p>
 * Copyright: (C), 2019/12/15 10:15
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class BroadcastConsumer {
	
	public static void main(String[] args) throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("broadcast_group");
		consumer.setNamesrvAddr("192.168.2.101:9876;192.168.2.102:9876");
		consumer.setConsumeThreadMin(1);
		consumer.setConsumeThreadMax(8);
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		//广播,全量消费
		consumer.setMessageModel(MessageModel.CLUSTERING);
		//consumer.setMessageModel(MessageModel.BROADCASTING);
		consumer.subscribe("TopicTestBroadcast", "TagA || TagB");
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				for (MessageExt ext : msgs) {
					System.out.printf(Thread.currentThread().getName() + " Receive New Message: " + new String(ext.getBody()) + "%n");
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		
		consumer.start();
		System.out.printf("Broadcast Consumer Started.%n");
	}
}
