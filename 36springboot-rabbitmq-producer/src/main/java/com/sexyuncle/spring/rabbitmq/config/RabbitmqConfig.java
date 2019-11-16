package com.sexyuncle.spring.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Copyright: (C), 2019/11/7 15:21
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
public class RabbitmqConfig {
	
	@Bean
	public DirectExchange tulingBootDirectExchange() {
		return new DirectExchange("springboot.direct.exchange", true, false);
	}
	
	@Bean
	public CustomExchange delayExchange() {
		Map<String, Object> args = new HashMap<>();
		args.put("x-delayed-type", "direct");
		return new CustomExchange("delayExchange", "x-delayed-message", true, false, args);
	}
	
	@Bean
	public Queue tulingBootQueue() {
		return new Queue("tulingBootQueue", true, false, false);
	}
	
	@Bean
	public Queue tulingClusterQueue() {
		return new Queue("tulingClusterQueue", true, false, false);
	}
	
	@Bean
	public Queue tulingBootDelayQueue() {
		return new Queue("tulingBootDelayQueue", true, false, false);
	}
	
	@Bean
	public Binding tulingBootBinder() {
		return BindingBuilder.bind(tulingBootQueue()).to(tulingBootDirectExchange()).with("springboot.key");
	}
	
	@Bean
	public Binding tulingClusterBinder() {
		return BindingBuilder.bind(tulingClusterQueue()).to(tulingBootDirectExchange()).with("rabbitmq.cluster.key");
	}
	
	@Bean
	public Binding binding() {
		return BindingBuilder.bind(tulingBootDelayQueue()).to(delayExchange()).with("springboot.delay.key").noargs();
	}
}
