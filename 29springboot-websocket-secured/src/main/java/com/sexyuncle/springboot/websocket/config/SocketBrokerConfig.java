package com.sexyuncle.springboot.websocket.config;

import static com.sexyuncle.springboot.websocket.Constants.SECURED_CHAT;
import static com.sexyuncle.springboot.websocket.Constants.SECURED_CHAT_HISTORY;
import static com.sexyuncle.springboot.websocket.Constants.SECURED_CHAT_ROOM;
import static com.sexyuncle.springboot.websocket.Constants.SECURED_CHAT_SPECIFIC_USER;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.sexyuncle.springboot.websocket.Constants;

@Configuration
@EnableWebSocketMessageBroker
public class SocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker(SECURED_CHAT_HISTORY, SECURED_CHAT_SPECIFIC_USER);
		registry.setApplicationDestinationPrefixes("/spring-security-mvc-socket");
		registry.setUserDestinationPrefix("/secured/user");
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(SECURED_CHAT_ROOM).withSockJS();
        registry.addEndpoint(SECURED_CHAT).withSockJS();
	}
}
