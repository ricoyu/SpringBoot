package com.sexyuncle.springboot.scp.config;

import org.springframework.beans.factory.config.MethodInvokingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

	@Bean
	public MethodInvokingBean jedisWarmUp() {
		MethodInvokingBean methodInvokingBean = new MethodInvokingBean();
		methodInvokingBean.setStaticMethod("com.loserico.cache.redis.JedisUtils.warmUp");
		return methodInvokingBean;
	}
	
}
