package com.sexyuncle.springboot.bootstrap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 2 is enabled through the @EnableSwagger2 annotation.
 * 
 * <p>
 * Copyright: Copyright (c) 2018-04-20 11:26
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * The configuration of Swagger mainly centers around the Docket bean.
	 * 
	 * After the Docket bean is defined, its select() method returns an instance of
	 * ApiSelectorBuilder, which provides a way to control the endpoints exposed by
	 * Swagger.
	 * 
	 * Predicates for selection of RequestHandlers can be configured with the help of
	 * RequestHandlerSelectors and PathSelectors. Using any() for both will make
	 * documentation for your entire API available through Swagger.
	 * 
	 * @return
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
}