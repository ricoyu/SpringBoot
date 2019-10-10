package com.sexyuncle.springboot.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @After 在 @AfterReturning 之前运行
 * <p>
 * Copyright: Copyright (c) 2018-02-27 11:10
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@Aspect
@Configuration
public class AfterAOPAspect {

	private static final Logger logger = LoggerFactory.getLogger(AfterAOPAspect.class);

	@AfterReturning(value = "execution(* com.sexyuncle.springboot.aop.service.*.*(..))", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		logger.info("{} returned the result {}", joinPoint, result);
	}

	@After(value = "execution(* com.sexyuncle.springboot.aop.service.*.*(..))")
	public void after(JoinPoint joinPoint) {
		logger.info("after execution of {}", joinPoint);
	}
}
