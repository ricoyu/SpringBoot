package com.sexyuncle.springboot.websocket.model;

import lombok.Data;

@Data
public class Message {

	private String from;
	private String text;

}