package com.loserico.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Copyright: (C), 2019/12/14 8:44
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class PullConsumer {
	
	private static final Map<MessageQueue, Long> offsetTable = new HashMap<>();
	
	public static void main(String[] args)
			throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
		DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("tl_message_group");
		consumer.setNamesrvAddr("192.168.2.101:9876;192.168.2.101:9876");
		consumer.start();
		
		Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("TopicStudent");
		for (MessageQueue mq : mqs) {
			System.err.println("Consume from the queue: " + mq);
			while (true) {
				PullResult pullResult = consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
				System.out.println(pullResult);
				putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
				
				switch (pullResult.getPullStatus()) {
					case FOUND:
						List<MessageExt> messageExtList = pullResult.getMsgFoundList();
						for (MessageExt m : messageExtList) {
							System.out.println(new String(m.getBody(), "UTF-8"));
						}
						break;
					case NO_MATCHED_MSG:
						break;
					case NO_NEW_MSG:
						break;
					case OFFSET_ILLEGAL:
						break;
					default:
						break;
				}
			}
		}
		consumer.shutdown();
	}
	
	private static void putMessageQueueOffset(MessageQueue mq, long offset) {
		offsetTable.put(mq, offset);
	}
	
	private static long getMessageQueueOffset(MessageQueue mq) {
		Long offset = offsetTable.get(mq);
		if (offset != null) {
			return offset;
		}
		return 0;
	}
}
