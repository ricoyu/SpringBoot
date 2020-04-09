package com.sexyuncle.springboot.hikari.config;

import com.loserico.orm.dao.JpaDao;
import com.sexyuncle.springboot.hikari.datasource.CompanyRoutingDataSource;
import com.sexyuncle.springboot.hikari.enums.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
	
	@Resource
	private DataSource barDataSource;
	
	@Resource
	private DataSource fooDataSource;

	@Resource
	private JpaProperties jpaProperties;

	@Autowired(required = false)
	private PersistenceUnitManager persistenceUnitManager;

	@Bean
	public DataSource dataSource() {
		CompanyRoutingDataSource dataSource = new CompanyRoutingDataSource();
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(Company.FOO, fooDataSource);
		targetDataSources.put(Company.BAR, barDataSource);
		dataSource.setTargetDataSources(targetDataSources);
		dataSource.setDefaultTargetDataSource(fooDataSource);
		return dataSource;
	}

	@Bean
	@Resource
	public PlatformTransactionManager txManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Primary
	@Bean(name = "entityManager")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(dataSource())
				.packages("com.sexyuncle.springboot.hikari.entity")
//				.persistenceUnit("barPersistenceUnit")
				.build();
	}

	@Bean
	public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
		AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		return new EntityManagerFactoryBuilder(adapter, jpaProperties.getProperties(), persistenceUnitManager);
	}
	
	@Bean
	public JpaDao jpaDao() {
		return new JpaDao();
	}
}
