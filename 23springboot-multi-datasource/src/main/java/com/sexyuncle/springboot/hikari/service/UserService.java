package com.sexyuncle.springboot.hikari.service;

import com.loserico.common.lang.concurrent.Concurrent;
import com.loserico.common.lang.concurrent.FutureResult;
import com.loserico.orm.dao.CriteriaOperations;
import com.loserico.orm.dao.EntityOperations;
import com.sexyuncle.springboot.hikari.aop.annotation.DynamicDataSource;
import com.sexyuncle.springboot.hikari.context.CompanyContextHolder;
import com.sexyuncle.springboot.hikari.entity.User;
import com.sexyuncle.springboot.hikari.enums.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
	
	@Autowired
	private EntityOperations entityOperations;

	@Autowired
	private CriteriaOperations criteriaOperations;
	
	public User findByUsername(String username, Company company) {
		User user = criteriaOperations.findUniqueByProperty(User.class, "username", username);
		log.info(user.toString());
		
//		CompanyContextHolder.setCompany(Company.BAR);
		user = criteriaOperations.findUniqueByProperty(User.class, "username", username);
		log.info(user.toString());
		
		return user;
	}
	
	public User findUserByUsernameAutoSwitch(String username) {
		return criteriaOperations.findUniqueByProperty(User.class, "username", username);
	}
	
	public User findUserInAnotherThread(String username) {
		FutureResult<User> futureResult = Concurrent
				.submit(() -> {
					log.info("Find user by {} in thread pool, current database {}", username, CompanyContextHolder.getCompany());
					User user = criteriaOperations.findUniqueByProperty(User.class, "username", username);
					return user;
				});
		Concurrent.await();
		return futureResult.get();
	}
	
	@DynamicDataSource(Company.BAR)
	public User findBar(String username) {
		return criteriaOperations.findUniqueByProperty(User.class, "username", username);
	}
	
	@DynamicDataSource(Company.FOO)
	public User findFoo(String username) {
		return criteriaOperations.findUniqueByProperty(User.class, "username", username);
	}
	
	@DynamicDataSource(Company.FOO)
	public void createUser(User user) {
		entityOperations.save(user);
	}
}
