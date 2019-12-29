package com.sexyuncle.springboot.testing;

import com.loserico.orm.dao.CriteriaOperations;
import com.loserico.orm.dao.EntityOperations;
import com.loserico.orm.dao.SQLOperations;
import com.sexyuncle.springboot.testing.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * It provides the following features over and above the regular Spring TestContext provided by @ContextConfiguration(classes=…​) annotation in spring-test.
 * <ul>
 * <li/>Automatically searches for a @SpringBootConfiguration when nested @Configuration class is not used, and no explicit classes are specified.
 * <li/>Allows custom environment properties to be defined using the properties attribute.
 * <li/>Provides support for different webEnvironment modes, including the ability to start a fully running web server listening on a defined or random port.
 * <li/>Registers a TestRestTemplate and/or WebTestClient bean for use in web tests that are using a fully running web server.
 * </ul>
 * <p>
 * <p>
 * Under the hood, @SpringBootTest tries to mimic the processes added by Spring Boot framework for creating the context
 * e.g. it decides what to scan based on package structures, loads external configurations from predefined locations,
 * optionally runs auto-configuration starters and so on.
 * <p>
 * As we see that this annotation starts and configure almost whole application before the test begin, we should use
 * SpringBootTest to write an integration tests that use the application processes and dependencies.
 * <p>
 * If we are writing unit tests then it’s always better now to use @SpringBootTest annotation.
 * Rather use the specialized spring boot test annotations which test a very specific slice of the application.
 *
 * <p>
 * Copyright: (C), 2019/12/27 15:34
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
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
		log.info("employee: {}", employee);
	}
}
