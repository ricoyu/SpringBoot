package com.sexyuncle.springboot.hikari.controller;

import com.sexyuncle.springboot.hikari.entity.Customers;
import com.sexyuncle.springboot.hikari.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public List<Customers> allCustomers() {
		return customerService.findAll();
	}
	
	@PostMapping("/customers")
	public Customers save(Customers customers) {
		return customerService.save(customers);
	}
}
