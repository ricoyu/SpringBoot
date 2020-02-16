package com.sexyuncle.springboot.lifecycle.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Copyright: (C), 2020/1/20 13:57
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Component
@Slf4j
public class NormalBeanOrdered2 implements InitializingBean, Ordered {
	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("<<<<<<<<<<<<< NormalBean.afterPropertiesSet >>>");
	}
	
	@Override
	public int getOrder() {
		return 9999;
	}
}
