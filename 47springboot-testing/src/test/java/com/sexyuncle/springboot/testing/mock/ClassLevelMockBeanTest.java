package com.sexyuncle.springboot.testing.mock;

import com.sexyuncle.springboot.testing.service.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * Copyright: (C), 2019/12/27 18:18
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@MockBean(EmployeeService.class)
public class ClassLevelMockBeanTest {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Test
	public void testClassLevelMock() {
		Assert.assertNotNull(employeeService);
	}
}
