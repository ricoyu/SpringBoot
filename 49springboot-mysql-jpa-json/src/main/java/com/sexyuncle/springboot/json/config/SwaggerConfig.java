package com.sexyuncle.springboot.json.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;

/**
 * <p>
 * Copyright: (C), 2020/1/4 16:50
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	/**
	 * Swagger also provides some default values in its response which you can
	 * customize, such as “Api Documentation”, “Created by Contact Email”, “Apache
	 * 2.0”.
	 *
	 * To change these values, you can use the apiInfo(ApiInfo apiInfo) method. The
	 * ApiInfo class that contains custom information about the API.
	 *
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfo(
				"My REST API",
				"Some custom description of API.",
				"API TOS",
				"Terms of service",
				new Contact("Rico Yu", "www.deepdatasense.com", "ricoyu520@gmail.com\""),
				"License of API", "API license URL", Collections.emptyList());
	}
	
	/**
	 * Custom Methods Response Messages Swagger allows globally overriding response
	 * messages of HTTP methods through Docket’s globalResponseMessage() method.
	 * First, you must instruct Swagger not to use default response messages.
	 *
	 * Suppose you wish to override 500 and 403 response messages for all GET methods.
	 * To achieve this, some code must be added to the Docket’s initialization block
	 * (original code is excluded for clarity):
	 *
	 * @return
	 */
	@Bean
	public Docket filtedApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.sexyuncle.springboot.json.controller"))
				.paths(PathSelectors.any())
				.build()
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, newArrayList(
						new ResponseMessageBuilder()
								.code(500)
								.message("500 message")
								.responseModel(new ModelRef("Error"))
								.build(),
						new ResponseMessageBuilder()
								.code(403)
								.message("Forbidden!")
								.build()))
				.apiInfo(apiInfo());
	}
}
