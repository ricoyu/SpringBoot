package com.sexyuncle.springboot.trace.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

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
@Slf4j
public class OrderController {
	
	@Resource
	private RestTemplate restTemplate;
	
	@PostMapping("/order")
	public String order(Integer count) {
		LinkedMultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
		request.add("count", count);
		String response = restTemplate.postForObject("http://localhost:8081/product/deduct", request, String.class);
		log.info("成功下单, {}", response);
		return response;
	}
}
