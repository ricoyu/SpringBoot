package com.sexyuncle.springboot.search.config;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sexyuncle.springboot.search.service.HibernateSearchService;

//@Configuration
public class HibernateSearchConfiguration {

	@Autowired
	private EntityManager entityManager;

	@Bean
	HibernateSearchService hibernateSearchService() {
//		HibernateSearchService hibernateSearchService = new HibernateSearchService();
		HibernateSearchService hibernateSearchService = new HibernateSearchService(entityManager);
		hibernateSearchService.initializeHibernateSearch();
		return hibernateSearchService;
	}
}