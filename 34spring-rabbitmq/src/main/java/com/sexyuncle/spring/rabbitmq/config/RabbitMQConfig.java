package com.sexyuncle.spring.rabbitmq.config;

import com.sexyuncle.spring.rabbitmq.converter.TulingImageConverter;
import com.sexyuncle.spring.rabbitmq.converter.TulingWordConverter;
import com.sexyuncle.spring.rabbitmq.delegate.TulingMsgDelegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * <p>
 * Copyright: (C), 2019/11/5 17:54
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
@Slf4j
public class RabbitMQConfig {
	
	/**
	 * 创建连接工厂, 注意这里的ConnectionFactory, CachingConnectionFactory都是Spring的
	 *
	 * @return ConnectionFactory
	 */
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses("192.168.2.103:5672");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("tuling");
		connectionFactory.setUsername("rico");
		connectionFactory.setPassword("123456");
		connectionFactory.setConnectionTimeout(100000);
		connectionFactory.setCloseTimeout(10000);
		return connectionFactory;
	}
	
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		//spring容器启动加载该类
		rabbitAdmin.setAutoStartup(true);
		return rabbitAdmin;
	}
	
	//=====================================申明三个交换机=================================================================
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange("tuling.topic.exchange", true, false);
	}
	
	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange("tuling.direct.exchange", true, false);
	}
	
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange("tuling.fanout.exchange", true, false);
	}
	
	//===========================================申明队列================================================================
	@Bean
	public Queue testTopicQueue() {
		return new Queue("testTopicQueue", true, false, false);
	}
	
	@Bean
	public Queue testTopicQueue2() {
		return new Queue("testTopicQueue2", true, false, false);
	}
	
	@Bean
	public Queue testDirectQueue() {
		return new Queue("testDirectQueue", true, false, false);
	}
	
	@Bean
	public Queue testFanoutQueue() {
		return new Queue("testfanoutQueue", true, false, false);
	}
	
	@Bean
	public Queue orderQueue() {
		return new Queue("orderQueue", true, false, false);
	}
	
	@Bean
	public Queue addressQueue() {
		return new Queue("addressQueue", true, false, false);
	}
	
	@Bean
	public Queue fileQueue() {
		return new Queue("fileQueue", true, false, false);
	}
	
	//========================================申明绑定===================================================================
	@Bean
	public Binding topicBingding() {
		return BindingBuilder.bind(testTopicQueue()).to(topicExchange()).with("topic.#");
	}
	
	@Bean
	public Binding topicBingding2() {
		return BindingBuilder.bind(testTopicQueue2()).to(topicExchange()).with("topic.key.#");
	}
	
	@Bean
	public Binding directBinding() {
		return BindingBuilder.bind(testDirectQueue()).to(directExchange()).with("direct.key");
	}
	
	@Bean
	public Binding orderQueueBinding() {
		return BindingBuilder.bind(orderQueue()).to(directExchange()).with("rabbitmq.order");
	}
	
	@Bean
	public Binding addressQueueBinding() {
		return BindingBuilder.bind(addressQueue()).to(directExchange()).with("rabbitmq.address");
	}
	
	@Bean
	public Binding fileQueueBinding() {
		return BindingBuilder.bind(fileQueue()).to(directExchange()).with("rabbitmq.file");
	}
	
	@Bean
	public Binding fanoutBinding() {
		return BindingBuilder.bind(testFanoutQueue()).to(fanoutExchange());
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
		rabbitTemplate.setReceiveTimeout(50000);
		return rabbitTemplate;
	}
	
	/**
	 * 消息监听容器
	 *
	 * @return
	 */
	@Bean
	public SimpleMessageListenerContainer simpleMessageListenerContainer() {
		SimpleMessageListenerContainer simpleMessageListenerContainer =
				new SimpleMessageListenerContainer(connectionFactory());
		//设置监听的队列
		simpleMessageListenerContainer.setQueues(testTopicQueue(), testDirectQueue(), testTopicQueue2(), orderQueue(), addressQueue(), fileQueue());
		//设置消费者数量, 启动时候开启2个线程去消费消息
		simpleMessageListenerContainer.setConcurrentConsumers(2);
		//最大消费者个数5
		simpleMessageListenerContainer.setMaxConcurrentConsumers(5);
		//设置签收模式
		simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
		//设置拒绝重回队列
		simpleMessageListenerContainer.setDefaultRequeueRejected(false);
		//消费端的标签策略
		simpleMessageListenerContainer.setConsumerTagStrategy(queue -> UUID.randomUUID().toString() + "-" + queue);
		
		/**
		 * 设置使用默认的监听方法
		 */
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new TulingMsgDelegate());
		//设置监听的方法
		messageListenerAdapter.setDefaultListenerMethod("consumerFileMessage");
		simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);
		
		//指定具体某一个队列被某一个消费者消费
		/*Map<String,String> queueOrTagToMethodName = new HashMap<>();
        queueOrTagToMethodName.put("testTopicQueue","consumerTopicQueue");
        queueOrTagToMethodName.put("testTopicQueue2","consumerTopicQueue2");
        messageListenerAdapter.setQueueOrTagToMethodName(queueOrTagToMethodName);*/
		
		/**
		 * 处理json
		 *
		 * 1:需要创建一个消息转换器对象
		 * 2:把消息转换器设置到消息监听适配器上
		 * 3:把监听器设置到容器中
		 
		 */
		 /*messageListenerAdapter.setDefaultListenerMethod("consumerJsonMessage");
		 Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
		 //Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter(objectMapper());
		 messageListenerAdapter.setMessageConverter(jackson2JsonMessageConverter);
		 simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);*/
		
		/*//===============================处理java对象得
		messageListenerAdapter.setDefaultListenerMethod("consumerJavaObjMessage");
		//消息转换器
		Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
		
		DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
		//版本问题 需要设置信任的包
		javaTypeMapper.setTrustedPackages("com.tuling.entity");
		//json转java得
		jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);
		//设置消息转换器
		messageListenerAdapter.setMessageConverter(jackson2JsonMessageConverter);
		//设置监听
		
		*/
		
		/**
		 * 处理pdf  image 等等
		 */
		//messageListenerAdapter.setDefaultListenerMethod("consumerFileMessage");
		//全局转换器
		ContentTypeDelegatingMessageConverter messageConverter = new ContentTypeDelegatingMessageConverter();
		messageConverter.addDelegate("img/png", new TulingImageConverter());
		messageConverter.addDelegate("img/jpg",new TulingImageConverter());
		messageConverter.addDelegate("application/word",new TulingWordConverter());
		messageConverter.addDelegate("word",new TulingWordConverter());
		
		
		messageListenerAdapter.setMessageConverter(messageConverter);
		//simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);
		
		return simpleMessageListenerContainer;
	}
}
