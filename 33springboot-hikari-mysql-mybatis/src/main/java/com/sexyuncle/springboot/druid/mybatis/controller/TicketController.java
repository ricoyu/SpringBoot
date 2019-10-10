package com.sexyuncle.springboot.druid.mybatis.controller;

import com.sexyuncle.springboot.druid.mybatis.entity.Ticket;
import com.sexyuncle.springboot.druid.mybatis.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class TicketController {

	@Autowired
	private TicketMapper ticketMapper;

	@GetMapping("/ticket/{id}")
	public Ticket getTicket(@PathVariable Long id) {
		return ticketMapper.findOne();
	}
}
