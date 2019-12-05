package com.sexyuncle.spring.rabbitmq.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class QueueingConsumer extends DefaultConsumer {
	
	private Channel channel;
	
	public QueueingConsumer(Channel channel) {
		super(channel);
		this.channel = channel;
	}
	
	@Override
	public void handleDelivery(String consumerTag,
							   Envelope envelope,
							   AMQP.BasicProperties properties,
							   byte[] body) throws IOException {
		try {
			System.out.println("消费消息: " + new String(body, StandardCharsets.UTF_8));
			channel.basicAck(envelope.getDeliveryTag(), false);
		} catch (IOException e) {
			log.error("", e);
			channel.basicReject(envelope.getDeliveryTag(), false);
		}
	}
	
}