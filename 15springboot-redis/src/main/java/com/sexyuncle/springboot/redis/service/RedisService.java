package com.sexyuncle.springboot.redis.service;

import com.loserico.cache.JedisUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>
 * Copyright: (C), 2021-01-21 10:59
 * <p>
 * <p>
 * Company: Information & Data Security Solutions Co., Ltd.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Component
public class RedisService {
	
	@PostConstruct
	public void init() {
		JedisUtils.set("k1", "v1");
		System.out.println(JedisUtils.get("k1"));
	}
}
