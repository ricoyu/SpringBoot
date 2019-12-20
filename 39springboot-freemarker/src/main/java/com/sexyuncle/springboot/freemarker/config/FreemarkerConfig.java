package com.sexyuncle.springboot.freemarker.config;

import cn.org.rapid_framework.freemarker.directive.BlockDirective;
import cn.org.rapid_framework.freemarker.directive.ExtendsDirective;
import cn.org.rapid_framework.freemarker.directive.OverrideDirective;
import cn.org.rapid_framework.freemarker.directive.SuperDirective;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Copyright: (C), 2019/12/19 9:25
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
public class FreemarkerConfig {
	
	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer freemarkerConfig = new FreeMarkerConfigurer();
		freemarkerConfig.setConfiguration(configuration().getObject());
		return freemarkerConfig;
	}
	
	@Bean
	public FreeMarkerConfigurationFactoryBean configuration() {
		FreeMarkerConfigurationFactoryBean configurationFactoryBean = new FreeMarkerConfigurationFactoryBean();
		configurationFactoryBean.setTemplateLoaderPath("classpath:/templates");
		configurationFactoryBean.setDefaultEncoding("utf-8");
		Map<String, Object> freemarkerVariables = new HashMap<String, Object>();
		freemarkerVariables.put("extends", new ExtendsDirective());
		freemarkerVariables.put("override", new OverrideDirective());
		freemarkerVariables.put("block", new BlockDirective());
		freemarkerVariables.put("super", new SuperDirective());
		configurationFactoryBean.setFreemarkerVariables(freemarkerVariables);
		return configurationFactoryBean;
	}
	
	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver() {
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver("", ".ftl");
		viewResolver.setOrder(1);
		viewResolver.setRequestContextAttribute("request");
		viewResolver.setExposeSpringMacroHelpers(true);
		viewResolver.setExposeRequestAttributes(true);
		viewResolver.setExposeSessionAttributes(true);
		viewResolver.setAllowSessionOverride(true);
		viewResolver.setContentType("text/html;charset=utf-8");
		viewResolver.setViewClass(org.springframework.web.servlet.view.freemarker.FreeMarkerView.class);
		return viewResolver;
	}
}
