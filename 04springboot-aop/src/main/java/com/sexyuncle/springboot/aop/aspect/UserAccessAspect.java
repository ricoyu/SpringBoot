package com.sexyuncle.springboot.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @Aspect: indicates that this is an Aspect
 * @Configuration: indicates that this file contains a Spring Bean Configuration for an Aspect.
 * @Before : We would want to execute the Aspect before the execution of the method
 * 
 * <p>
 * Copyright: Copyright (c) 2018-02-27 10:56
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@Aspect
@Configuration
public class UserAccessAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /*
     * What kind of method calls I would intercept 
     * execution(* PACKAGE.*.*(..)) 
     * Weaving & Weaver
     * 
     * ("execution(* com.in28minutes.springboot.tutorial.basics.example.aop.data.*.*(..))"
     * This defines the pointcut. 
     * We would want to intercept all method calls made to any methods in package 
     * com.sexyuncle.springboot.aop.service
     * 
     * 运行测试：com.sexyuncle.springboot.aop.BusinessAopSpringBootTest.invokeAOPStuff()
     * 之前可以看到这个before advice先执行
     * @on
     */
    @Before("execution(* com.sexyuncle.springboot.aop.service.*.*(..))")
    public void before(JoinPoint joinPoint) {
        //Advice
        logger.info(" Check for user access ");
        logger.info(" Allowed execution for {}", joinPoint);
    }
}