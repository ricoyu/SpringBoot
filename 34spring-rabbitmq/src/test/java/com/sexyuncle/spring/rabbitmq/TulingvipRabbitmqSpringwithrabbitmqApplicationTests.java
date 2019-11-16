package com.sexyuncle.spring.rabbitmq;

import com.loserico.common.lang.utils.IOUtils;
import com.loserico.json.jackson.JacksonUtils;
import com.sexyuncle.spring.rabbitmq.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * Copyright: (C), 2019/11/5 20:45
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TulingvipRabbitmqSpringwithrabbitmqApplicationTests {
	
	@Autowired
	private RabbitAdmin rabbitAdmin;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Test
	public void testContextLoads() {
		
	}
	
	@Test
	public void testTopicExchange() {
		String exchangeName = "rabbitadmin.topic.exchange";
		String queueName = "rabbitadmin.topic.queue";
		String routingKey = "rabbitadmin.#";
		
		//声明一个交换机
		TopicExchange topicExchange = new TopicExchange(exchangeName, true, false);
		rabbitAdmin.declareExchange(topicExchange);
		
		//申明一个队列
		Queue queue = new Queue(queueName, true, false, false);
		rabbitAdmin.declareQueue(queue);
		
		//申明一个绑定
		Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, null);
		rabbitAdmin.declareBinding(binding);
	}
	
	@Test
	public void testDirectExchange() {
		String directExchangeName = "rabbitadmin.direct.exchange";
		String queueName = "rabbitadmin.direct.queue";
		String routingKey = "rabbitadmin.key.#";
		
		DirectExchange directExchange = new DirectExchange(directExchangeName, true, false);
		rabbitAdmin.declareExchange(directExchange);
		
		Queue queue = new Queue(queueName, true, false, false);
		rabbitAdmin.declareQueue(queue);
		rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(directExchange).with(routingKey));
	}
	
	@Test
	public void testRabbitTemplate() {
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.getHeaders().put("company", "tuling");
		messageProperties.getHeaders().put("name", "sanshaoye");
		
		String exchangeName = "tuling.direct.exchange";
		String routingKey = "direct.key";
		String msgBody = "hello tuling";
		
		Message message = new Message(msgBody.getBytes(StandardCharsets.UTF_8), messageProperties);
		//rabbitTemplate.send(exchangeName, routingKey, message);
		
		//不需要Message对象发送
		rabbitTemplate.convertAndSend(exchangeName, routingKey, msgBody);
	}
	
	@Test
	public void testSimpleMessageListenerContainer() {
		rabbitTemplate.convertAndSend("tuling.topic.exchange", "topic.xixi", "你好 图灵");
	}
	
	@Test
	public void testMessageListenerAdaperQueueOrTagToMethodName() {
		String exchangeName = "tuling.topic.exchange";
		String routingKey1 = "topic.xixi";
		String routingKey2 = "topic.key.xixi";
		
		rabbitTemplate.convertAndSend(exchangeName, routingKey1, "你好 图灵");
		rabbitTemplate.convertAndSend(exchangeName, routingKey2, "你好 三少爷");
	}
	
	@Test
	public void testSendJson() {
		Order order = new Order();
		order.setOrderNo(UUID.randomUUID().toString());
		order.setCreateDt(new Date());
		order.setPayMoney(10000.00);
		order.setUserName("三少爷");
		
		String json = JacksonUtils.toJson(order);
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("application-json");
		//加上__TypeId__属性, 在TulingMsgDelegate那边调用的就是consumerJsonMessage(Order order), 不加的话由consumerJsonMessage(Map jsonMap)消费
		//messageProperties.getHeaders().put("__TypeId__", "com.sexyuncle.spring.rabbitmq.entity.Order");
		Message message = new Message(json.getBytes(StandardCharsets.UTF_8), messageProperties);
		rabbitTemplate.convertAndSend("tuling.direct.exchange", "rabbitmq.order", message);
	}
	
	@Test
	public void testSendImage() {
		byte[] data = IOUtils.readFileAsBytes("E:\\图片\\我\\QpEvMPQDQKMEKVNY20180918112246.png");
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("img/png");
		Message message = new Message(data, messageProperties);
		rabbitTemplate.convertAndSend("tuling.direct.exchange", "rabbitmq.file", message);
	}
	
	@Test
	public void testSendWordFile() {
		byte[] data = IOUtils.readFileAsBytes("D:\\Dropbox\\doc\\懂得及时止损的人，运气不会太差.docx");
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("application/word");
		Message message = new Message(data, messageProperties);
		rabbitTemplate.send("tuling.direct.exchange", "rabbitmq.file", message);
	}
}
