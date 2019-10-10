package com.sexyuncle.springboot.auartz.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loserico.commons.jackson.ObjectMapperFactoryBean;
import com.loserico.orm.jpa.dao.JpaDao;
import com.sexyuncle.springboot.auartz.jobs.factory.AutowiringSpringBeanJobFactory;

@Configuration
@EnableTransactionManagement
@ComponentScans(value = { @ComponentScan("com.sexyuncle.springboot.aop.service") })
public class AppConfig {

	@Bean
	public JpaDao jpaDao() {
		JpaDao jpaDao = new JpaDao();
		return jpaDao;
	}

	/**
	 * The bean definition doing several things:-
	 * JobFactory - The default is Spring’s AdaptableJobFactory, which supports java.lang.Runnable objects as well as standard Quartz org.quartz.Job instances. Note that this default only applies to a local Scheduler, not to a RemoteScheduler (where setting a custom JobFactory is not supported by Quartz).
	 * ThreadPool - Default is a Quartz SimpleThreadPool with a pool size of 10. This is configured through the corresponding Quartz properties.
	 * SchedulerFactory - The default used here is the StdSchedulerFactory, reading in the standard quartz.properties from quartz.jar.
	 * JobStore - The default used is RAMJobStore which does not support persistence and is not clustered.
	 * Life-Cycle - The SchedulerFactoryBean implements org.springframework.context.SmartLifecycle and org.springframework.beans.factory.DisposableBean which means the life-cycle of the scheduler is managed by the Spring container. The sheduler.start() is called in the start() implementation of SmartLifecycle after initialization and the scheduler.shutdown() is called in the destroy() implementation of DisposableBean at application teardown.
	 * You can override the startup behaviour by setting setAutoStartup(..) to false. With this setting you have to manually start the scheduler.
	 * @param applicationContext
	 * @return
	 * @on
	 */
	@Bean
	public SchedulerFactoryBean schedulerFactory(ApplicationContext applicationContext) {
		SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		
		factoryBean.setJobFactory(jobFactory);
		factoryBean.setApplicationContextSchedulerContextKey("applicationContext");
		return factoryBean;
	}
	
	@Bean
	@Primary
	public ObjectMapper objectMapper() throws Exception {
		return new ObjectMapperFactoryBean().getObject();
	}
}