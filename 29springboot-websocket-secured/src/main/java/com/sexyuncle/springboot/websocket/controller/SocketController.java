package com.sexyuncle.springboot.websocket.controller;

import static com.sexyuncle.springboot.websocket.Constants.SECURED_CHAT;
import static com.sexyuncle.springboot.websocket.Constants.SECURED_CHAT_HISTORY;
import static com.sexyuncle.springboot.websocket.Constants.SECURED_CHAT_SPECIFIC_USER;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.sexyuncle.springboot.websocket.Constants;
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
public class SocketController {
	
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping(SECURED_CHAT)
    @SendTo(SECURED_CHAT_HISTORY)
    public OutputMessage sendAll(Message msg) throws Exception {
        OutputMessage out = new OutputMessage(msg.getFrom(), msg.getText(), new SimpleDateFormat("HH:mm").format(new Date()));
        return out;
    }
    
    /**
     * Example of sending message to specific user using 'convertAndSendToUser()' and '/queue'
     */
    @MessageMapping(Constants.SECURED_CHAT_ROOM)
    public void sendSpecific(@Payload Message msg, Principal user, @Header("simpSessionId") String sessionId) throws Exception {
        OutputMessage out = new OutputMessage(msg.getFrom(), msg.getText(), new SimpleDateFormat("HH:mm").format(new Date()));
        simpMessagingTemplate.convertAndSendToUser(msg.getTo(), SECURED_CHAT_SPECIFIC_USER, out);
    }
    
	/**
	 * All subscribers to the “/topic/messages” destination will receive the
	 * message.
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@MessageMapping("/secured/chat")
	@SendTo("/secured/history")
	public OutputMessage greeting(Message message) throws Exception {
		String time = new SimpleDateFormat("HH:mm").format(new Date());
		return new OutputMessage(message.getFrom(), message.getText(), time);
	}
}
