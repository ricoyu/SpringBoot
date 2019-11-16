package com.sexyuncle.spring.rabbitmq.component;

import com.loserico.json.jackson.JacksonUtils;
import com.sexyuncle.spring.rabbitmq.entity.Order;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * Copyright: (C), 2019/11/7 15:36
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Component
public class TulingMsgSender {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private TulingConfirmCallBack tulingConfirmCallBack;
	
	@Autowired
	private TulingReturnCallback tulingReturnCallback;
	
	public void sendMsg(Object msg, Map<String, Object> props) {
		MessageHeaders messageHeaders = new MessageHeaders(props);
		//构建消息对象
		Message message = MessageBuilder.createMessage(msg, messageHeaders);
		
		//构建correlationData 用于做可靠性投递得,ID:必须为全局唯一的 根据业务规则
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		//开启确认模式
		rabbitTemplate.setConfirmCallback(tulingConfirmCallBack);
		//开启消息可达监听
		rabbitTemplate.setReturnCallback(tulingReturnCallback);
		
		rabbitTemplate.convertAndSend("springboot.direct.exchange", "springboot.key", message, correlationData);
		rabbitTemplate.convertAndSend("springboot.direct.exchange", "springboot.key2", message, correlationData);
	}
	
	public void sendOrderMsg(Order order) {
		//构建correlationData 用于做可靠性投递得,ID:必须为全局唯一的 根据业务规则
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		
		//开启确认模式
		rabbitTemplate.setConfirmCallback(tulingConfirmCallBack);
		//开启消息可达监听
		rabbitTemplate.setReturnCallback(tulingReturnCallback);
		
		MessageProperties messageProperties = new MessageProperties();
		org.springframework.amqp.core.Message message =
				new org.springframework.amqp.core.Message(JacksonUtils.toJson(order).getBytes(StandardCharsets.UTF_8), messageProperties);
		rabbitTemplate.convertAndSend("springboot.direct.exchange", "springboot.key3", message, correlationData);
		
		
		/**
		 * 发送Messaging包下的message对象
		 
		 Map<String,Object> map = new HashMap<>();
		 map.put("company","tuling");
		 MessageHeaders messageHeaders = new MessageHeaders(map);
		 ObjectMapper objectMapper = new ObjectMapper();
		 String orderJson = objectMapper.writeValueAsString(order);
		 Message message = MessageBuilder.createMessage(orderJson,messageHeaders);
		 System.out.println("orderJson"+orderJson);
		 rabbitTemplate.convertAndSend("springboot.direct.exchange","springboot.key3",message,correlationData);
		 */
		
		//直接发送对象
		//rabbitTemplate.convertAndSend("springboot.direct.exchange","springboot.key3",order,correlationData);
	}
	
	public void sendDelayMessage(Order order) {
		//构建correlationData 用于做可靠性投递得,ID:必须为全局唯一的 根据业务规则
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		//开启确认模式
		rabbitTemplate.setConfirmCallback(tulingConfirmCallBack);
		
		//开启消息可达监听
		//rabbitTemplate.setReturnCallback(tulingReturnCallback);
		
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
		
		rabbitTemplate.convertAndSend("delayExchange", "springboot.delay.key", order, (message) -> {
					message.getMessageProperties().setHeader("x-delay", 10000); //设置延迟时间
					return message;
				},
				correlationData);
	}
}
