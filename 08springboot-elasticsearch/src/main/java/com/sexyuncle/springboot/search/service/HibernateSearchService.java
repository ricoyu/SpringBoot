package com.sexyuncle.springboot.search.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sexyuncle.springboot.search.entity.BaseballCard;

@Service
@Transactional
public class HibernateSearchService {
	
	private static final Logger logger = LoggerFactory.getLogger(HibernateSearchService.class);

	@Autowired
	private EntityManager entityManager;

	public HibernateSearchService() {

	}
	
	public HibernateSearchService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	public void initializeHibernateSearch() {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			logger.error("", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<BaseballCard> fuzzySearch(String searchTerm) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder()
				.forEntity(BaseballCard.class)
				.get();
		Query luceneQuery = queryBuilder.keyword()
				.fuzzy()
				.withEditDistanceUpTo(1)
				.withPrefixLength(1)
				.onFields("name")
				.matching(searchTerm)
				.createQuery();

		javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, BaseballCard.class);

		List<BaseballCard> baseballCardList = null;
		try {
			baseballCardList = jpaQuery.getResultList();
		} catch (NoResultException nre) {
			logger.error("", nre);
		}

		return baseballCardList;
	}
}