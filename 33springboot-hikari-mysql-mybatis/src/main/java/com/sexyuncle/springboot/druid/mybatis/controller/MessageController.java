package com.sexyuncle.springboot.druid.mybatis.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 问题件
 * <p>
 * Copyright: Copyright (c) 2019-10-09 11:31
 * <p>
 * Company: Sexy Uncle Inc.
 * <p>
 *
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@RestController
public class MessageController {

	@Value("${message}")
	private String message;

	@GetMapping("/message")
	public String message() {
		return message;
	}
}
