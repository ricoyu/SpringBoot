package com.sexyuncle.springboot.hikari.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.sexyuncle.springboot.hikari.aop.annotation.DynamicDataSource;
import com.sexyuncle.springboot.hikari.context.CompanyContextHolder;
import com.sexyuncle.springboot.hikari.enums.Company;

import lombok.extern.slf4j.Slf4j;

/**
 * 在Spring Security基于token认证完成之后, 先把token对应的公司放到CompanyContextHolder里面
 * 这个每个Service方法在查数据库的时候取的都是对应公司的数据库. 
 * 同时每个业务方法还可以加注 @DynamicDataSource(FOO)来显式指定这个方法取哪个公司的数据库
 * <p>
 * Copyright: Copyright (c) 2019-05-28 13:36
 * <p>
 * Company: Sexy Uncle Inc.
 * <p>
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@Component
@Aspect
@Slf4j
public class DynamicDataSourceAspect {

	@Before("@annotation(com.sexyuncle.springboot.hikari.aop.annotation.DynamicDataSource)")
	public void switchDataSource(JoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		DynamicDataSource dynamicDataSource = methodSignature.getMethod().getAnnotation(DynamicDataSource.class);
		if (dynamicDataSource != null) {
			Company company = dynamicDataSource.value() != null ? dynamicDataSource.value()
					: CompanyContextHolder.getCompany();
			if (company != null) {
				log.info("Switch to {}'s database", company);
				CompanyContextHolder.setCompany(company);
			}
		}
	}
}
