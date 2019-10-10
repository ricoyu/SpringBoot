package com.sexyuncle.springboot.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configurable
public class RedisConfig {

	@Autowired
	private JedisPool jedisPool;
	
	@Bean
	@Scope(value="prototype")
	public Jedis jedis() {
		return jedisPool.getResource();
	}
}
