package com.sexyuncle.springboot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Api("用户管理接口")
@Controller
public class SimpleController {

	@Value("${spring.application.name}")
	private String appName;

	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("appName", appName);
		return "home";
	}
	
	@ApiOperation("新增用户")
	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("appName", appName);
		return "home";
	}

}
