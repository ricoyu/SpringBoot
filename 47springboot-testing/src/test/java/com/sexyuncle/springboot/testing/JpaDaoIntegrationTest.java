package com.sexyuncle.springboot.testing;

import com.loserico.orm.dao.CriteriaOperations;
import com.loserico.orm.dao.EntityOperations;
import com.loserico.orm.dao.SQLOperations;
import com.sexyuncle.springboot.testing.entity.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * <p>
 * Copyright: (C), 2019/12/27 15:34
 * <p>
 * Company: Sexy Uncle Inc.
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
//@Import(AppTestConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaDaoIntegrationTest {
	
	@Autowired
	private EntityOperations entityOperations;
	
	@Autowired
	private CriteriaOperations criteriaOperations;
	
	@Autowired
	private SQLOperations sqlOperations;
	
	@Test
	public void testGetById() {
		Employee employee = entityOperations.get(Employee.class, 1L);
		Assert.assertNotNull(employee);
		Assert.assertEquals(employee.getName(), "张三");
	}
}
