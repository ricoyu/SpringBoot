package com.sexyuncle.springboot.sharding.service;

import com.loserico.orm.dao.EntityOperations;
import com.loserico.orm.dao.SQLOperations;
import com.sexyuncle.springboot.sharding.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
@Transactional
public class OrderService {
	
	@Autowired
	private EntityOperations entityOperations;
	
	@Autowired
	private SQLOperations sqlOperations;
	
	public Order save(Order order) {
		entityOperations.save(order);
		return order;
	}
}
