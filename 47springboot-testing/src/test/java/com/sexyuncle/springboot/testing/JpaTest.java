package com.sexyuncle.springboot.testing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * In Spring boot applications, we can use @DataJpaTest annotation that focuses only on JPA components. 
 * This annotation will disable full auto-configuration and instead apply only configuration relevant to JPA tests.
 * 只启用JPA相关的配置, 其他Auto-configuration都禁用掉了
 * By default, tests annotated with @DataJpaTest are transactional and roll back at the end of each test.
 *
 * By default, it scans for @Entity classes and configures Spring Data JPA repositories annotated with @Repository annotation.
 *
 * If an embedded database is available on the classpath, it configures one as well.
 * <p>
 * Copyright: (C), 2019/12/27 16:51
 * <p>
 * Company: Sexy Uncle Inc.
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaTest {
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	private EntityManager entityManager;
	
	@Test
	public void testEntityManagerFactoryConfigured() {
		Assert.assertNotNull(entityManagerFactory);
	}
	
	@Test
	public void testEntityManagerConfigured() {
		Assert.assertNotNull(entityManager);
	}
}
