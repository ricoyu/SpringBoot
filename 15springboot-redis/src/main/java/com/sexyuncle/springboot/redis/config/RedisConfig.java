package com.sexyuncle.springboot.redis.config;

import com.loserico.common.lang.context.ApplicationContextHolder;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class RedisConfig {
	
	
	@Bean
	public ApplicationContextHolder applicationContextHolder() {
		return new ApplicationContextHolder();
	}
	
}
