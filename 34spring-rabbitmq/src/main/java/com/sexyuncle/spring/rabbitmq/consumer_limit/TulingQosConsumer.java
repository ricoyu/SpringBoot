package com.sexyuncle.spring.rabbitmq.consumer_limit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * <p>
 * Copyright: (C), 2019/11/4 21:13
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class TulingQosConsumer extends DefaultConsumer {
	
	private Channel channel;
	
	public TulingQosConsumer(Channel channel) {
		super(channel);
		this.channel = channel;
	}
	
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
							   byte[] body) throws IOException {
		System.out.println("consumerTag:" + consumerTag);
		System.out.println("envelope:" + envelope);
		System.out.println("properties:" + properties);
		System.out.println("body:" + new String(body));
		
		/**
		 * 正常签收的情况
		 * multiple:false标识不批量签收
		 */
		//channel.basicAck(envelope.getDeliveryTag(), false);
		/**
		 * 发生业务异常, 不签收, 拒绝消息
		 */
		channel.basicReject(envelope.getDeliveryTag(), false);
	}
}
