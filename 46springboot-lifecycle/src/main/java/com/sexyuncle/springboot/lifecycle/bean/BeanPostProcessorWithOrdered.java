package com.sexyuncle.springboot.lifecycle.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
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
public class BeanPostProcessorWithOrdered implements BeanPostProcessor, Ordered {
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		log.info("<<<<<<<<<<<<< BeanPostProcessorWithOrdered.postProcessBeforeInitialization 10000 >>>" + beanName);
		return bean;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.info("<<<<<<<<<<<<< BeanPostProcessorWithOrdered.postProcessAfterInitialization 10000 >>>" + beanName);
		return bean;
	}
	
	@Override
	public int getOrder() {
		return 10000;
	}
}
