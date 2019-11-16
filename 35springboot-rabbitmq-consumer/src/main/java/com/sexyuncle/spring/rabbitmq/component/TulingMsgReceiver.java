package com.sexyuncle.spring.rabbitmq.component;

import com.loserico.common.lang.utils.DateUtils;
import com.loserico.json.jackson.JacksonUtils;
import com.rabbitmq.client.Channel;
import com.sexyuncle.spring.rabbitmq.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * <p>
 * Copyright: (C), 2019/11/7 14:47
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Component
@Slf4j
public class TulingMsgReceiver {
	
	@RabbitListener(queues = {"tulingBootQueue"})
	@RabbitHandler
	public void consumerMsg(Message message, Channel channel) throws IOException {
		log.info("消费消息:{}", message.getPayload());
		//手工签收
		Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		log.info("接受deliveryTag:{}", deliveryTag);
		channel.basicAck(deliveryTag, false);
	}
	
	@RabbitListener(queues = {"tulingBootDelayQueue"})
	@RabbitHandler
	public void consumerDelayMsg(org.springframework.amqp.core.Message message, Channel channel) throws IOException {
		byte[] data = message.getBody();
		Order order = JacksonUtils.toObject(data, Order.class);
		log.info("在{},签收:{}", DateUtils.format(new Date()), order);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	/**
	 * 强烈不推荐这种用法 我们再开发中需要把队列 交换机  绑定配置到我们专门的配置类中
	 *
	 * @param message
	 * @param channel
	 * @throws IOException
	 */
	@RabbitListener(bindings =
	@QueueBinding(
			value = @Queue(
					name = "tulingBootQueue2",
					durable = "true",
					autoDelete = "false",
					exclusive = "false"
			),
			exchange = @Exchange(
					name = "springboot.direct.exchange",
					type = "direct",
					durable = "true",
					autoDelete = "false"
			),
			key = {"springboot.key2"}
	)
	)
	public void consumerMsg2(Message message, Channel channel) throws IOException {
		log.info("consumerMsg2===消费消息:{}", message.getPayload());
		//手工签收
		Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		log.info("consumerMsg2===接收deliveryTag:{}", deliveryTag);
		channel.basicAck(deliveryTag, false);
	}
	
	
}
