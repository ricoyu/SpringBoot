package com.sexyuncle.springboot.websocket.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.sexyuncle.springboot.websocket.model.Message;
import com.sexyuncle.springboot.websocket.model.OutputMessage;

/**
 * 处理Websocket消息
 * <p>
 * Copyright: Copyright (c) 2019-06-27 09:54
 * <p>
 * Company: Sexy Uncle Inc.
 * <p>
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@RestController
@Slf4j
public class MessageController {

	/**
	 * All subscribers to the “/topic/messages” destination will receive the
	 * message.
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@MessageMapping("/greeting")
	@SendTo("/topic/messages")
	public OutputMessage greeting(Message message) throws Exception {
		String time = new SimpleDateFormat("HH:mm").format(new Date());
		return new OutputMessage(message.getFrom(), message.getText(), time);
	}
}
