package com.sexyuncle.springboot.webjars.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Copyright: (C), 2019/12/7 21:11
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RestController
@RequestMapping
public class WebJarsController {
	
	@GetMapping("/hello")
	public String hello() {
		return "hello " + System.currentTimeMillis(); 
	}
}
