package com.sexyuncle.springboot.lifecycle.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Copyright: (C), 2020/1/20 13:54
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Component
@Slf4j
public class BeanPostProcessorWithPriorityOrdered2 implements BeanPostProcessor, PriorityOrdered {
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		log.info("<<<<<<<<<<<<< BeanPostProcessorWithPriorityOrdered2.postProcessBeforeInitialization 9999 >>>" + beanName);
		return bean;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.info("<<<<<<<<<<<<< BeanPostProcessorWithPriorityOrdered2.postProcessAfterInitialization 9999 >>>" + beanName);
		return bean;
	}
	
	@Override
	public int getOrder() {
		return 9999;
	}
}
