package com.sexyuncle.springboot.trace.controller;

import com.sexyuncle.springboot.trace.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/order")
@Slf4j
@Api("订单服务")
public class OrderController {
	
	@Resource
	private RestTemplate restTemplate;
	
	@ApiOperation(value = "创建订单")
	@PostMapping("")
	public String order(Integer count, HttpServletRequest request) {
		LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("count", count);
		String response = restTemplate.postForObject("http://localhost:8082/product/deduct", params, String.class);
		log.info("成功下单, {}", response);
		return response;
	}
	
	@ApiOperation(value = "更新订单详情", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PostMapping(value = "/update", consumes = "application/json")
	public OrderVO update(@RequestBody OrderVO orderVO) {
		return orderVO;
	}
}
