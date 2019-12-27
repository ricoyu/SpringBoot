package com.loserico.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * ONEWAY 比较简单, 发出去后, 什么都不管直接返回
 * <p>
 * Copyright: (C), 2019/12/12 21:13
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class OnewayProducer {
	
	public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
		DefaultMQProducer producer = new DefaultMQProducer("tl_message_group");
		// Specify name server addresses.
		producer.setNamesrvAddr("192.168.2.101:9876;192.168.2.102:9876");
		
		producer.setSendMsgTimeout(10000);
		
		producer.start();
		for (int i = 0; i < 10; i++) {
			Message msg = new Message("TopicTestOneWay" /* Topic */,
					"TagSendOneWay" /* Tag */,
					"OrderID198",
					("Hello RocketMQ test i " +
							i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
			);
			producer.sendOneway(msg);
		}
		//Shut down once the producer instance is not longer in use.
		producer.shutdown();
	}
}
