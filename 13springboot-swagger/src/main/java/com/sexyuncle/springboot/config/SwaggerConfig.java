package com.sexyuncle.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;

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
//@Configuration
//@EnableSwagger2
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
	/*@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}*/

	/**
	 * It is not always desirable to expose the documentation for your entire API. You
	 * can restrict Swagger’s response by passing parameters to the apis() and paths()
	 * methods of the Docket class.
	 * 
	 * As seen above, RequestHandlerSelectors allows using the any or none predicates,
	 * but can also be used to filter the API according to the base package, class
	 * annotation, and method annotations.
	 * 
	 * PathSelectors provides additional filtering with predicates which scan the
	 * request paths of your application. You can use any(), none(), regex(), or
	 * ant().
	 * 
	 * In the example below, we will instruct Swagger to include only controllers from
	 * a particular package, with specific paths, using the ant() predicate.
	 * 
	 * @return
	 */
	/*@Bean
	public Docket filtedApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.sexyuncle.springboot.controller"))
				//.paths(PathSelectors.any())
				//.paths(PathSelectors.ant("/home/*"))
				.paths(PathSelectors.ant("/home"))
				.build();
	}*/
	
	/*@Bean
	public Docket filtedApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.sexyuncle.springboot.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}*/
	
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
				.apis(RequestHandlerSelectors.basePackage("com.sexyuncle.springboot.controller"))
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
