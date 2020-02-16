package com.sexyuncle.springboot.lifecycle.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Copyright: (C), 2020/1/20 13:52
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Component
@Slf4j
public class BeanPostProcessorWithoutPrioritiedOrder implements BeanPostProcessor {
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		log.info("<<<<<<<<<<<<< BeanPostProcessorWithoutPrioritiedOrder.postProcessBeforeInitialization >>>" + beanName);
		return bean;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.info("<<<<<<<<<<<<< BeanPostProcessorWithoutPrioritiedOrder.postProcessAfterInitialization >>>" + beanName);
		return bean;
	}
}
