package com.sexyuncle.springboot.properties;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@Configuration
@PropertySource("classpath:kuaidiniao.properties")
@ConfigurationProperties()
@Validated
public class ExpressConfig {
	
	@PostConstruct
	public void init() {
		System.out.println(apiKey);
	}

	@NotBlank
	private String apiKey;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
