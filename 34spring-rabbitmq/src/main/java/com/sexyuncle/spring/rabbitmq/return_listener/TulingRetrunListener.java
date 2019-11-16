package com.sexyuncle.spring.rabbitmq.return_listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * Copyright: (C), 2019/11/5 9:32
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class TulingRetrunListener implements ReturnListener {
	
	@Override
	public void handleReturn(int replyCode, String replyText, String exchange, String routingKey,
							 AMQP.BasicProperties properties, byte[] body) throws IOException {
		System.out.println("replyCode:" + replyCode);
		System.out.println("replyText:" + replyText);
		System.out.println("exchange:" + exchange);
		System.out.println("routingKey:" + routingKey);
		System.out.println("properties:" + properties);
		System.out.println("msg body:" + new String(body, StandardCharsets.UTF_8));
	}
}
