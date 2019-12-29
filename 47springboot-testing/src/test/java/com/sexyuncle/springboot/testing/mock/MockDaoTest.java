package com.sexyuncle.springboot.testing.mock;

import com.loserico.orm.dao.EntityOperations;
import com.loserico.orm.dao.JpaDao;
import com.sexyuncle.springboot.testing.service.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * Copyright: (C), 2019/12/27 18:26
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@MockBeans({@MockBean(EmployeeService.class), @MockBean(JpaDao.class)})
public class MockDaoTest {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EntityOperations entityOperations;
	
	@Test
	public void testRepeatableMock() {
		Assert.assertNotNull(employeeService);
		Assert.assertNotNull(entityOperations);
	}
}
