package com.loserico.rocketmq.batch;

import lombok.SneakyThrows;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Copyright: (C), 2019/12/15 16:57
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class BatchConsumer {
	
	public static void main(String[] args) throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("batchGroup");
		consumer.setNamesrvAddr("192.168.2.101:9876;192.168.2.102:9876");
		consumer.subscribe("BatchTest", "*");
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			@SneakyThrows
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				/*for (MessageExt msg : msgs) {
					System.out.println("queueId=" + msg.getQueueId() + "," + new String(msg.getBody()));
				}*/
				MessageExt msg = msgs.get(0);
				String textMessage = new String(msg.getBody());
				System.out.println("queueId=" + msg.getQueueId() + "," + textMessage);
				TimeUnit.SECONDS.sleep(1);
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		
		consumer.start();
		System.out.printf("Consumer Started.%n");
	}
}
