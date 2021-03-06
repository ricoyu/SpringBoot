package com.sexyuncle.springboot.hikari;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import com.loserico.commons.jackson.JacksonUtils;
import com.loserico.commons.utils.ReflectionUtils;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EntityManagerVerify {

	@Autowired
	private List<EntityManager> entityManagers;

	@Autowired
	private List<DataSource> dataSources;

	@PostConstruct
	public void init() {
		for (EntityManager entityManager : entityManagers) {
			Object targetEntityManager = ReflectionUtils.getFieldValue("targetFactory",
					ReflectionUtils.getFieldValue("h", entityManager));
			LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = (LocalContainerEntityManagerFactoryBean) ReflectionUtils
					.getFieldValue("entityManagerFactoryBean", ReflectionUtils.getFieldValue("h", targetEntityManager));
			String beanName = (String) ReflectionUtils.getFieldValue("beanName", entityManagerFactoryBean);
			log.info("Bean Name: {}", beanName);
			log.info("Persistence Unit Name: {}", entityManagerFactoryBean.getPersistenceUnitName());
		}

		System.out.println(((HikariDataSource) dataSources.get(0)).getPoolName());
		System.out.println(((HikariDataSource) dataSources.get(2)).getPoolName());

	}
}
