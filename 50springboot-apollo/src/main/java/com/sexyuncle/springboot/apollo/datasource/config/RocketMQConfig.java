package com.sexyuncle.springboot.apollo.datasource.config;

import com.zl.framework.rocketmq.ServiceBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Copyright: (C), 2020/1/19 15:46
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
public class RocketMQConfig {
	
	@Bean
	//@ConfigurationProperties(prefix = "rocketmq")
	public ServiceBeanPostProcessor serviceBeanPostProcessor() {
		return new ServiceBeanPostProcessor();
	}
}
