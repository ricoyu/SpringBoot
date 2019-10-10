package com.sexyuncle.springboot.search.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScans(value = { @ComponentScan(value = "com.mulberrylearning.datastatistic.service",
		scopedProxy = ScopedProxyMode.TARGET_CLASS) })
public class AppConfig {

}
