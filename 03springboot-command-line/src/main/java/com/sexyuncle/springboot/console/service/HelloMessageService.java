package com.sexyuncle.springboot.console.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HelloMessageService {

	@Value("${name:unknown}")
	private String name; //默认值unknown

	public String getMessage() {
		return getMessage(name);
	}

	public String getMessage(String name) {
		return "Hello " + name;
	}

}