package com.sexyuncle.spring.rabbitmq;

import com.loserico.json.jackson.JacksonUtils;
import com.sexyuncle.spring.rabbitmq.component.TulingMsgSender;
import com.sexyuncle.spring.rabbitmq.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * Copyright: (C), 2019/11/7 19:40
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StringBootRabbitMQProducerTest {
	
	@Autowired
	private TulingMsgSender tulingMsgSender;
	
	@Test
	public void testMsgSender() {
		Map<String, Object> msgProps = new HashMap<>();
		msgProps.put("company","tuling");
		msgProps.put("name","rico");
		
		Order order = new Order();
		order.setOrderNo(UUID.randomUUID().toString());
		order.setUserName("rico");
		order.setPayMoney(10000.00);
		order.setCreateDt(new Date());
		
		tulingMsgSender.sendMsg(JacksonUtils.toJson(order), msgProps);
	}
	
	@Test
	public void testSenderOrder() {
		Order order = new Order();
		order.setOrderNo(UUID.randomUUID().toString());
		order.setUserName("smlz");
		order.setPayMoney(10000.00);
		order.setCreateDt(new Date());
		tulingMsgSender.sendOrderMsg(order);
	}
	
	@Test
	public void testSenderDelay() {
		Order order = new Order();
		order.setOrderNo(UUID.randomUUID().toString());
		order.setUserName("smlz");
		order.setPayMoney(10000.00);
		order.setCreateDt(new Date());
		
		tulingMsgSender.sendDelayMessage(order);
	}
}
