package com.loserico.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 异步发送
 * <p>
 * Copyright: (C), 2019/12/12 20:53
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class AsyncProducer {
	
	public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
		DefaultMQProducer producer = new DefaultMQProducer("tl_message_group");
		producer.setNamesrvAddr("192.168.2.101:9876;192.168.2.102:9876");
		producer.start();
		
		producer.setRetryTimesWhenSendFailed(5);
		
		int messageCount = 5;
		final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
		for (int i = 0; i < messageCount; i++) {
			final int index = i;
			//Message msg = new Message("TopicTestTag", "TagB", "OrderID188", ("I m sending msg content is ssy" + i).getBytes(StandardCharsets.UTF_8));
			Message msg = new Message("TopicIdempotency", ("message" + i).getBytes());
			//消息发送成功后，执行回调函数
			producer.send(msg, new SendCallback() {
				
				@Override
				public void onSuccess(SendResult sendResult) {
					System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
					System.out.printf("%s%n", sendResult);
					countDownLatch.countDown();
				}
				
				@Override
				public void onException(Throwable e) {
					System.out.printf("%-10d Exception %s %n", index, e);
					e.printStackTrace();
					countDownLatch.countDown();
				}
			});
		}
		
		countDownLatch.await(5, TimeUnit.SECONDS);
		producer.shutdown();
	}
}
