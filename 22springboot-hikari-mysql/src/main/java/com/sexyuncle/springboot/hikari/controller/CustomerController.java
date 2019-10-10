package com.sexyuncle.springboot.hikari.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sexyuncle.springboot.hikari.entity.Customers;
import com.sexyuncle.springboot.hikari.service.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public List<Customers> allCustomers() {
		return customerService.findAll();
	}
}
