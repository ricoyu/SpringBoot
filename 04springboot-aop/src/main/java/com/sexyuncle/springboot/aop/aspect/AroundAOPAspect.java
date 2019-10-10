package com.sexyuncle.springboot.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * We can add an Aspect defining what should be done when the TrackTime annotation is used
 * <p>
 * Copyright: Copyright (c) 2018-02-27 11:14
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@Aspect
@Configuration
public class AroundAOPAspect {

	private static final Logger logger = LoggerFactory.getLogger(AroundAOPAspect.class);

	/*
	 * @Around uses an around advice. It intercepts the method call and uses joinPoint.proceed() to execute the method.
	 * @annotation(com.in28minutes.springboot.tutorial.basics.example.aop.TrackTime) 
	 * is the pointcut to define interception based on an annotation — @annotation followed by the complete type name of the annotation.
	 * 
	 * @on
	 */
	@Around("@annotation(com.sexyuncle.springboot.aop.annotation.TrackTime)")
	public void around(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		joinPoint.proceed();
		long timeTaken = System.currentTimeMillis() - startTime;
		logger.info("Time Taken by {} is {}", joinPoint, timeTaken);
	}
	
	@Around("com.sexyuncle.springboot.aop.pointcuts.CommonJoinPointConfig.businessLayerExecutionAnnotation()")
	public void aroundWithCommonPointcutsAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		joinPoint.proceed();
		long timeTaken = System.currentTimeMillis() - startTime;
		logger.info("Time Taken by {} is {}", joinPoint, timeTaken);
	}
	
	@Around("com.sexyuncle.springboot.aop.pointcuts.CommonJoinPointConfig.businessLayerExecution()")
	public void aroundWithCommonPointcuts(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		joinPoint.proceed();
		long timeTaken = System.currentTimeMillis() - startTime;
		logger.info("Time Taken by {} is {}", joinPoint, timeTaken);
	}
}
