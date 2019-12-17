package com.loserico.rocketmq.broadcast;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * Copyright: (C), 2019/12/15 9:53
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class BroadcastProducer {
	
	public static void main(
			String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
		DefaultMQProducer producer = new DefaultMQProducer("broadcast_group");
		producer.setNamesrvAddr("192.168.2.101:9876;192.168.2.102:9876");
		producer.start();
		
		for (int i = 0; i < 4; i++) {
			Message message =
					new Message("TopicTestBroadcast", "TagA", "OrderId188", ("HelloWorld" + i).getBytes(StandardCharsets.UTF_8));
			SendResult sendResult = producer.send(message);
			System.out.printf("%s%n", sendResult);
		}
		
		producer.shutdown();
	}
}
