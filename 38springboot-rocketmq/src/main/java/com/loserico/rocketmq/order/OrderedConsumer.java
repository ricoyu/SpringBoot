package com.loserico.rocketmq.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * Copyright: (C), 2019/12/24 9:57
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class OrderedConsumer {
	
	public static void main(String[] args) throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ordered_group_name");
		consumer.setNamesrvAddr("192.168.2.102:9876;192.168.2.102:9876");
		
		/**
		 * 设置消费位置
		 */
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		consumer.subscribe("TopicOrdered", "*");
		
		consumer.registerMessageListener(new MessageListenerOrderly() {
			AtomicLong consumeTimes = new AtomicLong(0);
			
			@Override
			public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
				context.setAutoCommit(true);
				Random random = new Random();
				for (MessageExt msg : msgs) {
					// 可以看到每个queue有唯一的consume来消费, 订单对每个queue(分区)有序
					try {
						System.out.println("consumeThread=" + Thread.currentThread().getName()
								+ ", queueId=" + msg.getQueueId()
								+ ", content:" + new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				
				try {
					//模拟业务逻辑处理中...
					TimeUnit.SECONDS.sleep(random.nextInt(10));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return ConsumeOrderlyStatus.SUCCESS;
			}
		});
		
		consumer.start();
		System.out.printf("Consumer Started.%n");
	}
}
