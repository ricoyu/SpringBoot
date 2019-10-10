package com.sexyuncle.springboot.hikari.jpa;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CheckEntityManager {
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	private EntityManager entityManager;
	
	@PostConstruct
	public void showEntityManagerProperty() {
		Map<String, Object> properties = entityManager.getProperties();
		for (String property : properties.keySet()) {
			System.out.println("property: " + property);
			System.out.println("value: " + properties.get(property));
		}
	}
	
	@PostConstruct
	public void showConnection() throws SQLException {
		log.info("Datasource: " + dataSource.toString());
		Connection connection = dataSource.getConnection();
		log.info("Connection: " + connection.toString());
		connection.close();
	}
}
