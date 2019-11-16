package com.sexyuncle.spring.rabbitmq.ack_nack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * Copyright: (C), 2019/11/4 14:53
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class TulingAckConsumer extends DefaultConsumer {
	
	private Channel channel;
	
	public TulingAckConsumer(Channel channel) {
		super(channel);
		this.channel = channel;
	}
	
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
							   byte[] body) throws IOException {
		try {
			//模拟业务
			Integer marker = (Integer) properties.getHeaders().get("marker");
			if (marker != 0) {
				System.out.println("消费消息: " + new String(body, StandardCharsets.UTF_8));
				channel.basicAck(envelope.getDeliveryTag(), false);
			} else {
				throw new RuntimeException("模拟业务异常");
			}
		} catch (Exception e) {
			System.out.println("异常消费消息: " + new String(body, StandardCharsets.UTF_8));
			/**
			 * 重回队列
			 * 重回队列后重回的消息会被再次消费, 这个例子里面就是第0条消息不断消费不断重回队列, 造成一个死循环
			 * 
			 * envelope.getDeliveryTag() 是消息的唯一ID
			 * 上半场ACK的唯一ID
			 * multiple 是否支持批量签收/批量拒绝
			 * requeue  消费失败了, 消息是否允许重新回到队里里面去  
			 */
			channel.basicNack(envelope.getDeliveryTag(), false, true);
			//不重回队列
			//channel.basicNack(envelope.getDeliveryTag(), false, false);
		}
	}
}
