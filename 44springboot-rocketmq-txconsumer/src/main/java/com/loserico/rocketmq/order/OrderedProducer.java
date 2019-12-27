package com.loserico.rocketmq.order;

import lombok.Data;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Copyright: (C), 2019/12/24 9:23
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class OrderedProducer {
	
	public static void main(
			String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
		DefaultMQProducer producer = new DefaultMQProducer("ordered_group_name");
		producer.setNamesrvAddr("192.168.2.101:9876;192.168.2.102:9876");
		producer.start();
		
		String[] tags = new String[]{"TagA", "TagB", "TagC"};
		// 订单列表
		List<OrderStep> orderList = buildOrders();
		
		String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		for (int i = 0; i < 10; i++) {
			// 加个时间前缀
			String body = dateStr + "Hello RocketMQ" + i + " " + orderList.get(i);
			Message msg = new Message("TopicOrdered",
					tags[i % tags.length],
					"KEY" + i,
					body.getBytes(RemotingHelper.DEFAULT_CHARSET));
			
			SendResult sendResult = producer.send(msg,
					(messageQueues, message, arg) -> {
						Long orderId = (Long) arg; //根据订单id选择发送queue
						int index = (int) (orderId % messageQueues.size());
						return messageQueues.get((int) index);
					},
					orderList.get(i).getOrderId()); //订单ID
			
			System.out.println(String.format("SendResult status:%s, queueId:%d, body:%s",
					sendResult.getSendStatus(),
					sendResult.getMessageQueue().getQueueId(),
					body));
		}
		
		producer.shutdown();
	}
	
	/**
	 * 生成模拟订单数据
	 */
	private static List<OrderStep> buildOrders() {
		List<OrderStep> orderList = new ArrayList<OrderStep>();
		
		OrderStep orderDemo = new OrderStep();
		orderDemo.setOrderId(15103111039L);
		orderDemo.setDesc("创建");
		orderList.add(orderDemo);
		
		orderDemo = new OrderStep();
		orderDemo.setOrderId(15103111065L);
		orderDemo.setDesc("创建");
		orderList.add(orderDemo);
		
		orderDemo = new OrderStep();
		orderDemo.setOrderId(15103111039L);
		orderDemo.setDesc("付款");
		orderList.add(orderDemo);
		
		orderDemo = new OrderStep();
		orderDemo.setOrderId(15103117235L);
		orderDemo.setDesc("创建");
		orderList.add(orderDemo);
		
		orderDemo = new OrderStep();
		orderDemo.setOrderId(15103111065L);
		orderDemo.setDesc("付款");
		orderList.add(orderDemo);
		
		orderDemo = new OrderStep();
		orderDemo.setOrderId(15103117235L);
		orderDemo.setDesc("付款");
		orderList.add(orderDemo);
		
		orderDemo = new OrderStep();
		orderDemo.setOrderId(15103111065L);
		orderDemo.setDesc("完成");
		orderList.add(orderDemo);
		
		orderDemo = new OrderStep();
		orderDemo.setOrderId(15103111039L);
		orderDemo.setDesc("推送");
		orderList.add(orderDemo);
		
		orderDemo = new OrderStep();
		orderDemo.setOrderId(15103117235L);
		orderDemo.setDesc("完成");
		orderList.add(orderDemo);
		
		orderDemo = new OrderStep();
		orderDemo.setOrderId(15103111039L);
		orderDemo.setDesc("购物车");
		orderList.add(orderDemo);
		
		return orderList;
	}
	
	/**
	 * 订单的步骤
	 */
	@Data
	private static class OrderStep {
		private long orderId;
		private String desc;
	}
}
