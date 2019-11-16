package com.sexyuncle.spring.rabbitmq.return_listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * <p>
 * Copyright: (C), 2019/11/5 9:43
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class ListenerConsumer extends DefaultConsumer {
	
	public ListenerConsumer(Channel channel) {
		super(channel);
	}
	
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
							   byte[] body) throws IOException {
		System.out.println("接收的消息:" + new String(body));
	}
}