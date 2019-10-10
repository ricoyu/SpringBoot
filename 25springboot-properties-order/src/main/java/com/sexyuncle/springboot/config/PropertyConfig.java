package com.sexyuncle.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value="property-source.properties")
public class PropertyConfig {

}
