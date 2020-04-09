package com.sexyuncle.springboot.hikari.controller;

import com.loserico.common.lang.context.ApplicationContextHolder;
import com.sexyuncle.springboot.hikari.entity.User;
import com.sexyuncle.springboot.hikari.enums.Company;
import com.sexyuncle.springboot.hikari.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/user")
	public User create(User user) {
		userService.createUser(user);
		return user;
	}
	
	@GetMapping("/user/search")
	public User findUser(String username, Company company) {
		DataSourceProperties dataSourceProperties = ApplicationContextHolder.getBean("barDataSourceProperties", DataSourceProperties.class);
		return userService.findByUsername(username, company);
	}
	
	@GetMapping("/user/bar")
	public User findBarUser(String username) {
		return userService.findBar(username);
	}
	
	@GetMapping("/user/foo")
	public User findFooUser(String username) {
		User user = userService.findFoo(username);
		return user;
	}
	
	@GetMapping("/user/auto")
	public User findUserAutoSwitch(String username) {
		return userService.findUserByUsernameAutoSwitch(username);
	}
	
	@GetMapping("/user/thread")
	public User findUserInAnotherThread(String username) {
		return userService.findUserInAnotherThread(username);
	}
}
