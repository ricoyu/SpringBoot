package com.sexyuncle.springboot.hikari.service;

import com.loserico.orm.dao.EntityOperations;
import com.sexyuncle.springboot.hikari.entity.Customers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@Slf4j
public class CustomerService {

	@Autowired
	private EntityOperations entityOperations;
	
	public List<Customers> findAll() {
		return entityOperations.findAll(Customers.class);
	}
	
	public Customers save(Customers customers) {
		entityOperations.save(customers);
		return customers;
	}
}
