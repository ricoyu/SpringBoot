package com.sexyuncle.springboot.hikari.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loserico.orm.dao.EntityOperations;
import com.sexyuncle.springboot.hikari.entity.Customers;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {

	@Autowired
	private EntityOperations entityOperations;
	
	public List<Customers> findAll() {
		return entityOperations.findAll(Customers.class);
	}

}
