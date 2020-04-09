package com.sexyuncle.springboot.scp.config;

import com.loserico.common.spring.annotation.processor.PostInitializeGroupOrderedBeanProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskConfig {

	@Bean
	public PostInitializeGroupOrderedBeanProcessor postInitializeBeanProcessor() {
		PostInitializeGroupOrderedBeanProcessor beanProcessor = new PostInitializeGroupOrderedBeanProcessor();
		beanProcessor.setContextCount(1);
		return beanProcessor;
	}
}
