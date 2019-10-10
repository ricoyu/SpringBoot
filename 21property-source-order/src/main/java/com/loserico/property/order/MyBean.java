package com.loserico.property.order;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Data
@Slf4j
public class MyBean {

	@Value("${name}")
	private String name;
	
	@PostConstruct
	public void printName() {
		log.info(name);
	}
}
