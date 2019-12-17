package com.loserico.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Copyright: (C), 2019/12/13 13:48
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class OrderedProducer {
	
	public static void main(
			String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
		DefaultMQProducer producer = new DefaultMQProducer("orderedGroup");
		producer.setNamesrvAddr("192.168.2.101:9876;192.168.2.102:9876");
		producer.start();
		
		String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
		final CountDownLatch countDownLatch = new CountDownLatch(100);
		for (int i = 0; i < 100; i++) {
			int orderId = i % 10;
			Message msg = new Message("TopicOrdered", tags[i % tags.length], "KEY" + i,
					("RocketMQ Ordered Msg" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
			
			SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
				@Override
				public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
					Integer id = (Integer) arg;
					int index = id % mqs.size();
					countDownLatch.countDown();
					return mqs.get(index);
				}
			}, orderId);
		}
		
		countDownLatch.await(1, TimeUnit.MINUTES);
		System.out.println("done");
		producer.shutdown();
	}
}
