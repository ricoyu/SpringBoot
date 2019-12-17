package com.sexyuncle.springboot.freemarker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * Copyright: (C), 2019/12/17 9:52
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Controller
@RequestMapping(value= "/freemarker")
public class FreemarkerController {
	
	/**
	 * Method to display the index page of the application.
	 * @param name
	 * @return
	 */
	@GetMapping(value= "/welcome/{name}")
	public ModelAndView welcome(@PathVariable(value= "name") String name) {
		ModelAndView model = new ModelAndView();
		model.addObject("name", name);
		model.setViewName("welcome");
		
		return model;
	}
}
