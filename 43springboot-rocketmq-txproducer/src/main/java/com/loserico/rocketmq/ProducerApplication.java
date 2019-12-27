package com.loserico.rocketmq;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {
	
	private static final String TX_PGROUP_NAME = "myTxProducerGroup";
	
	@Resource
	private RocketMQTemplate rocketMQTemplate;
	
	@Value("${tl.rocketmq.transTopic}")
	private String springTransTopic;
	
	@Value("${tl.rocketmq.topic}")
	private String springTopic;
	
	@Value("${tl.rocketmq.orderTopic}")
	private String orderPaymentTopic;
	
	@Value("${tl.rocketmq.msgExtTopic}")
	private String msgExtTopic;
	
	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		SendResult sendResult = null;
		//1: 发送字符串
		/*String msg = "Hello World!";
		sendResult = rocketMQTemplate.syncSend(springTopic, msg);
		System.out.println("Send message: " + msg);*/
		// 使用自定义的templete
		
		//2: 发送 spring Message,本质也是发送字符串
		/*sendResult = rocketMQTemplate.syncSend(springTopic, MessageBuilder.withPayload("tl rocketmq msg test").build());
		System.out.printf("syncSend2 to topic %s sendResult=%s %n", springTopic, sendResult);*/
		
		//3: 发送自定义对象-Dto
		/*rocketMQTemplate.asyncSend(springTopic,
				new OrderPaymentDto(10102303, new BigDecimal("88.00")),
				new SendCallback() {
					@Override
					public void onSuccess(SendResult sendResult) {
						System.out.printf("async onSucess SendResult=%s %n", sendResult);
					}
					
					@Override
					public void onException(Throwable e) {
						System.out.printf("async onException Throwable=%s %n", e);
					}
				});*/
		
		//4: 发送指定Tag的msg
		/*rocketMQTemplate.convertAndSend(msgExtTopic + ":tag0", "I'm from tag0");
		System.out.printf("syncSend topic %s tag %s %n", msgExtTopic, "tag0");
		rocketMQTemplate.convertAndSend(msgExtTopic + ":tag1", "I'm from tag1");
		System.out.printf("syncSend topic %s tag %s %n", msgExtTopic, "tag1");*/
		
		// 批量发送字符消息
		//sendBatchMessages();
		
		// 发送事务消息
		testTransaction();
	}
	
	/**
	 * 批量消息
	 */
	private void sendBatchMessages() {
		List<Message> msgs = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			msgs.add(MessageBuilder.withPayload("Hello RocketMQ Batch Msg#" + i)
					.setHeader(RocketMQHeaders.KEYS, "KEY_" + i).build());
		}
		SendResult sendResult = rocketMQTemplate.syncSend(springTopic, msgs, 60000);
		System.out.printf("--- Batch messages send result :" + sendResult);
	}
	
	/**
	 * 发送事务消息
	 */
	private void testTransaction() {
		String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
		for (int i = 0; i < 1; i++) {
			Message msg = MessageBuilder.withPayload("Hello RocketMQ" + i)
					.setHeader(RocketMQHeaders.KEYS, "KEY_" + i)
					.build();
			/**
			 * TX_PGROUP_NAME 必须同 {@link TransactionListener} 类的注解 txProducerGroup
			 * @RocketMQTransactionListener(txProducerGroup = "myTxProducerGroup")
			 * 1: 发完这个事务消息
			 * 2: com.loserico.rocketmq.trans.TransactionListener#executeLocalTransaction(Message, Object)
			 *    会自动执行, 把业务Service注入到这个TransactionListener, 在executeLocalTransaction里面执行业务方法
			 * 3: com.loserico.rocketmq.trans.TransactionListener#checkLocalTransaction(Message)
			 *    这个是在上一个方法返回UNKNOWN状态后, 回查本地事务状态的一个回调方法, 本地事务状态可以根据之前生成的业务ID, 比如订单号, 
			 *    去数据库里面查一下存不存在就知道上一个事务有没有执行成功了
			 */
			SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(TX_PGROUP_NAME,
					springTopic + ":" + tags[i % tags.length],
					msg, null);
			System.out.printf("------ send Transactional msg body = %s , sendResult=%s %n",
					msg.getPayload(), sendResult.getSendStatus());
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
