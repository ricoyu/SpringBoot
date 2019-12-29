package com.sexyuncle.springboot.testing.mock;

import com.sexyuncle.springboot.testing.service.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 在配置类里面 Mock Bean
 * <p>
 * Copyright: (C), 2019/12/27 18:21
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RunWith(SpringRunner.class)
public class ConfigurationLevelMockTest {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Test
	public void testClassLevelMock() {
		Assert.assertNotNull(employeeService);
	}
	
	@Configuration
	static class Config{
		
		@MockBean
		private EmployeeService employeeService;
	}
}
