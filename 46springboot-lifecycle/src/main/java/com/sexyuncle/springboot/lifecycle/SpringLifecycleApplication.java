package com.sexyuncle.springboot.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @SpringBootApplication 注解等价于以下三个注解之和
 * @Configuration, @EnableAutoConfiguration, and @ComponentScan
 *
 * <p>
 * Copyright: Copyright (c) 2018-04-16 10:54
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@SpringBootApplication
@Slf4j
public class SpringLifecycleApplication implements ResourceLoaderAware,
		ApplicationEventPublisherAware,
		MessageSourceAware,
		BeanFactoryAware,
		ApplicationContextAware,
		BeanNameAware,
		BeanPostProcessor,
		InitializingBean {
	
	private static final AtomicInteger counter = new AtomicInteger(1);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringLifecycleApplication.class, args);
	}
	
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		log.info("=========={}. ResourceLoaderAware.setResourceLoader(resourceLoader) ==============================================", counter.getAndIncrement());
	}
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		log.info("=========={}. ApplicationEventPublisher.setApplicationEventPublisher(applicationEventPublisher) ===================", counter.getAndIncrement());
	}
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		log.info("=========={}. MessageSourceAware.setMessageSource(messageSource) ==================================================", counter.getAndIncrement());
	}
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		log.info("=========={}. BeanFactoryAware.setBeanFactory(beanFactory) ========================================================", counter.getAndIncrement());
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		log.info("=========={}. ApplicationContextAware.setApplicationContext(applicationContext) ====================================", counter.getAndIncrement());
	}
	
	@Override
	public void setBeanName(String name) {
		log.info("=========={}. BeanNameAware.setBeanName(name) =======================================================================", counter.getAndIncrement());
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		log.info("=========={}. BeanPostProcessor.postProcessBeforeInitialization(bean, beanName) " + bean.getClass().getSimpleName() + "=======================================================================", counter.getAndIncrement());
		return bean;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.info("=========={}. BeanPostProcessor.postProcessAfterInitialization(bean, beanName) " + bean.getClass().getSimpleName() + "=======================================================================", counter.getAndIncrement());
		return bean;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("=========={}. InitializingBean.afterPropertiesSet() =======================================================================", counter.getAndIncrement());
	}
}
