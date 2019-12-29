package com.sexyuncle.springboot.testing.controller;

import com.sexyuncle.springboot.testing.entity.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/
 * <p>
 * Copyright: (C), 2019/12/27 18:34
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RestController
@RequestMapping("")
public class EmployeeController {
	
	@GetMapping(value = "/employees")
	public List<Employee> getAllEmployees() {
		return null;
	}
	
	@GetMapping("/employee/{id}")
	public Employee getEmployeeById(@PathVariable Integer id) {
		return null;
	}
}
