package com.loserico.rocketmq.filter;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * Copyright: (C), 2019/12/15 19:27
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class FilterProducer {
	
	public static void main(
			String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
		DefaultMQProducer producer = new DefaultMQProducer("filterGroup");
		producer.setNamesrvAddr("192.168.2.101:9876;192.168.2.102:9876");
		producer.start();
		
		for (int i = 0; i < 5; i++) {
			Message message =
					new Message("TopicFilter", "Tag-Filter", ("Hello Rocket" + i).getBytes(StandardCharsets.UTF_8));
			message.putUserProperty("a", i + "");
			if (i % 2 == 0) {
				message.putUserProperty("b", "zaizai");
			} else {
				message.putUserProperty("b", "sanshaoye");
			}
			producer.send(message);
		}
		
		producer.shutdown();
	}
}
