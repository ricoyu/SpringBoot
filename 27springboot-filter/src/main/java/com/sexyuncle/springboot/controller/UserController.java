package com.sexyuncle.springboot.controller;

import static java.util.Collections.emptyList;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@GetMapping()
	public List<String> getAllUsers() {
		return emptyList();
	}
}