package com.sexyuncle.spring.rabbitmq.delegate;

import com.sexyuncle.spring.rabbitmq.entity.Order;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <p>
 * Copyright: (C), 2019/11/5 18:34
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class TulingMsgDelegate {
	
	public void handleMessage(byte[] msgBody) {
		System.out.println("TulingMsgDelegate......handleMessage"+new String(msgBody, StandardCharsets.UTF_8));
	}
	
	public void handleMessage(String msgBody) {
		System.out.println("TulingMsgDelegate......handleMessage: "+msgBody);
	}
	
	public void consumerMsg(String msg){
		System.out.println("TulingMsgDelegate......consumerMsg: "+msg);
	}
	
	public void consumerTopicQueue(String msgBody) {
		System.out.println("TulingMsgDelegate......consumerTopicQueue: "+msgBody);
		
	}
	
	public void consumerTopicQueue2(String msgBody) {
		System.out.println("TulingMsgDelegate......consumerTopicQueue2: "+msgBody);
		
	}
	
	/**
	 * 处理json
	 * @param jsonMap
	 */
	public void consumerJsonMessage(Map jsonMap) {
		System.out.println("TulingMsgDelegate ============================处理json: "+jsonMap);
	}	
	
	/**
	 * 处理order得
	 * @param order
	 */
	public void consumerJavaObjMessage(Order order) {
		System.out.println("TulingMsgDelegate ============================处理java对象: "+order.toString());
		
	}
	
	
	
	public void consumerFileMessage(File file) {
		System.out.println("TulingMsgDelegate========================处理文件: "+file.getName());
	}
}
