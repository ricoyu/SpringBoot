package com.sexyuncle.springboot.rabbitmq.custom_consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * <p>
 * Copyright: (C), 2019/11/4 11:17
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class TulingConsumer extends DefaultConsumer {
	
	public TulingConsumer(Channel channel) {
		super(channel);
	}
	
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
							   byte[] body) throws IOException {
		System.out.println("consumerTag:" + consumerTag);
		System.out.println("envelope:" + envelope);
		System.out.println("properties:" + properties);
		System.out.println("body:" + new String(body));
	}
}
