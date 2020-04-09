package com.sexyuncle.spring.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class LordthreeConsumer {
	
	@RabbitListener(queues = "directQueue")
	public void consumerMsg(Message message) {
		
		System.out.println("消费消息:" + message.getPayload().toString());
	}
	
	@RabbitListener(queues = "fanoutQueue1")
	public void consumerFanoutMsg(Message message) {
		System.out.println("消费消息:" + message.getPayload().toString());
	}
	
	@RabbitListener(queues = "fanoutQueue2")
	public void consumerFanoutMsg2(Message message) {
		System.out.println("消费消息:" + message.getPayload());
	}
	
	@RabbitListener(queues = "topicQueue")
	public void consumerTopicMsg(Message message) {
		System.out.println("消费消息:" + message.getPayload());
	}
	
	@RabbitListener(queues = "topicQueue2")
	public void consumerTopicMsg2(Message message) {
		System.out.println("消费消息:" + message.getPayload());
	}
	
}