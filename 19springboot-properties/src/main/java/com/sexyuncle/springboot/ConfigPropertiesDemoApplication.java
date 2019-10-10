package com.sexyuncle.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import com.sexyuncle.springboot.properties.ConfigProperties;
import com.sexyuncle.springboot.properties.CustomJsonProperties;
import com.sexyuncle.springboot.properties.JsonProperties;
import com.sexyuncle.springboot.properties.JsonPropertyContextInitializer;

/**
 * https://www.mkyong.com/spring-boot/spring-boot-configurationproperties-example/
 * <p>
 * Copyright: Copyright (c) 2019-03-21 16:13
 * <p>
 * Company: Sexy Uncle Inc.
 * <p>
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = { ConfigProperties.class, JsonProperties.class, CustomJsonProperties.class })
public class ConfigPropertiesDemoApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConfigPropertiesDemoApplication.class)
				.initializers(new JsonPropertyContextInitializer())
				.run();
	}

}