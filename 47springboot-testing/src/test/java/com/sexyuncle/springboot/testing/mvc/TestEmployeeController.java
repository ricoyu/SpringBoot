package com.sexyuncle.springboot.testing.mvc;

import com.sexyuncle.springboot.testing.controller.EmployeeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/
 * <p>
 * Copyright: (C), 2019/12/27 18:38
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class TestEmployeeController {
	
	@Autowired
	private MockMvc mvc;
	
	/**
	 * These tests hit the APIs, pass the path parameters using MockMvcRequestBuilders and verify the status response
	 * codes and response content using MockMvcResultMatchers and MockMvcResultHandlers.
	 * 会真正调用到EmployeeController#getAllEmployees()方法
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetAllEmployees() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.get("/employees")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.employees").exists())
				.andExpect(jsonPath("$.employees[*].id").isNotEmpty());
	}
}
