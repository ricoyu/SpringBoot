package com.sexyuncle.springboot.sharding.controller;

import com.loserico.web.vo.Result;
import com.loserico.web.vo.Results;
import com.sexyuncle.springboot.sharding.annotation.Router;
import com.sexyuncle.springboot.sharding.entity.Order;
import com.sexyuncle.springboot.sharding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Copyright: (C), 2020/2/16 12:56
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("")
	@Router(routingField = "userId")
	public Result save(Order order) {
		orderService.save(order);
		return Results.success().result(order.getOrderId());
	}
	
	@GetMapping("/{orderId}")
	public Result order(@PathVariable Long orderId) {
		Order order = orderService.findById(orderId);
		return Results.success().result(order);
	}
}
