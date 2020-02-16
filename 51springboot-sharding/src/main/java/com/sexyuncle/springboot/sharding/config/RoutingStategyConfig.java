package com.sexyuncle.springboot.sharding.config;

import com.loserico.sharding.core.RoutingDsAndTbStrategy;
import com.loserico.sharding.core.RoutingDsStrategy;
import com.loserico.sharding.core.RoutingStrategy;
import com.loserico.sharding.core.RoutingTbStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Copyright: (C), 2020/2/16 11:05
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
public class RoutingStategyConfig {
	
	@Bean
	@ConditionalOnProperty(prefix = "datasource.routing", name = "routingStategy", havingValue = "ROUTING_DS_TABLE_STATEGY")
	public RoutingStrategy routingDsAndTableStrategy() {
		return new RoutingDsAndTbStrategy();
	}
	
	@Bean
	@ConditionalOnProperty(prefix = "datasource.routing",name = "routingStategy",havingValue ="ROUTGING_DS_STATEGY")
	public RoutingStrategy routingDsStrategy() {
		return new RoutingDsStrategy();
	}
	
	@Bean
	@ConditionalOnProperty(prefix = "datasource.routing",name = "routingStategy",havingValue ="ROUTGIN_TABLE_STATEGY")
	public RoutingStrategy routingTbStategy() {
		return new RoutingTbStrategy();
	}
}
