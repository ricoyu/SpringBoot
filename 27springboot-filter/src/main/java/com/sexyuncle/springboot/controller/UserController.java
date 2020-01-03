package com.sexyuncle.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

@Slf4j
@RestController
@RequestMapping("")
public class UserController {

	@GetMapping("/users")
	public List<String> getAllUsers() {
		return emptyList();
	}
	
	@GetMapping("/user/{name}")
	public String getUserName(@PathVariable String name, HttpServletRequest request) {
		Map<String, Object> variables = (Map<String, Object>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		log.info("{}", variables);
		return name;
	}
}