package com.sexyuncle.springboot.sharding.controller;

import com.loserico.web.vo.Result;
import com.loserico.web.vo.Results;
import com.sexyuncle.springboot.sharding.annotation.Router;
import com.sexyuncle.springboot.sharding.datasource.RoutingDataSource;
import com.sexyuncle.springboot.sharding.entity.Order;
import com.sexyuncle.springboot.sharding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

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
	private RoutingDataSource routingDataSource;
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("")
	@Router(routingField = "userId")
	public Result save(Order order) throws SQLException {
		orderService.save(order);
		
		//Connection connection = routingDataSource.getConnection();
		//PreparedStatement preparedStatement =
		//		connection.prepareStatement("insert into t_order(user_id, money) values ("+order.getUserId()+", 123.98)");
		//preparedStatement.execute();
		return Results.success().result(order.getOrderId());
	}
	
	@GetMapping("/{userId}")
	public Result order(@PathVariable String userId) {
		List<Order> orders = orderService.findByUserId(userId);
		return Results.success().results(orders);
	}
}
