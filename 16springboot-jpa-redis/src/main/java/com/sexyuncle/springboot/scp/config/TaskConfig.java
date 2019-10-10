package com.sexyuncle.springboot.scp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.peacefish.spring.annotation.processor.PostInitializeGroupOrderedBeanProcessor;

@Configuration
public class TaskConfig {

	@Bean
	public PostInitializeGroupOrderedBeanProcessor postInitializeBeanProcessor() {
		PostInitializeGroupOrderedBeanProcessor beanProcessor = new PostInitializeGroupOrderedBeanProcessor();
		beanProcessor.setContextCount(1);
		return beanProcessor;
	}
}
