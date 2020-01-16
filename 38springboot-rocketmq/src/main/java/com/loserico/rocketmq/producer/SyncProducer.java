package com.loserico.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * 同步发送方式
 * <p>
 * Copyright: (C), 2019/12/11 21:09
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class SyncProducer {
	
	public static void main(
			String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
		DefaultMQProducer producer = new DefaultMQProducer("tl_msg_student_group");
		/*
		 * 从nameserver拉取broker的信息
		 */
		producer.setNamesrvAddr("192.168.2.101:9876;192.168.2.102:9876");
		//默认是3秒, 如果网络抖动或者比较慢的话可以调大这个参数
		producer.setSendMsgTimeout(10000);
		producer.start();
		for (int i = 0; i < 2000; i++) {
			Message message =
					new Message("TopicStudent", "TagStudent", "tag", ("Hello RocketMQ-"+i).getBytes(StandardCharsets.UTF_8));
			//同步方式发送消息
			SendResult sendResult = producer.send(message);
			/*
			 * SendResult [sendStatus=SEND_OK, msgId=C0A801A9418418B4AAC238180C8A0000, offsetMsgId=C0A8026500002A9F00000000000161A1, messageQueue=MessageQueue [topic=TopicStudent, brokerName=broker-a, queueId=0], queueOffset=0]
			 * brokerName=broker-a 发送到了这个broker
			 * queueId=0 发送到了第0个queue
			 */
			System.out.printf("%s%n", sendResult);
		}
		/*Message message =
				new Message("TopicStudent", "TagStudent", "tag", "Hello RocketMQ20191214".getBytes(StandardCharsets.UTF_8));
		//同步方式发送消息
		SendResult sendResult = producer.send(message);
		*//*
		 * SendResult [sendStatus=SEND_OK, msgId=C0A801A9418418B4AAC238180C8A0000, offsetMsgId=C0A8026500002A9F00000000000161A1, messageQueue=MessageQueue [topic=TopicStudent, brokerName=broker-a, queueId=0], queueOffset=0]
		 * brokerName=broker-a 发送到了这个broker
		 * queueId=0 发送到了第0个queue
		 *//*
		System.out.printf("%s%n", sendResult);*/
		System.out.println("done");
		producer.shutdown();
	}
}
