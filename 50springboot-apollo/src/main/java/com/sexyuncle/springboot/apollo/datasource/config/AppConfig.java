package com.sexyuncle.springboot.apollo.datasource.config;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.loserico.common.spring.transaction.TransactionEvents;
import com.loserico.orm.dao.JpaDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class AppConfig {
	
	@Bean
	public JpaDao jpaDao() {
		return new JpaDao();
	}
	
	@Bean
	public TransactionEvents transactionEvents() {
		return new TransactionEvents();
	}
	
	/**
	 * 默认监听的是application命名空间
	 * @param changeEvent
	 */
	@ApolloConfigChangeListener
	public void onChange(ConfigChangeEvent changeEvent) {
		Set<String> changedKeys = changeEvent.changedKeys();
		for (String key : changedKeys) {
			ConfigChange change = changeEvent.getChange(key);
			System.out.println(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s",
					change.getPropertyName(),
					change.getOldValue(),
					change.getNewValue(),
					change.getChangeType()));
		}priorityOrderedPostProcessors.forEach((o) -> System.out.println(o.getClass().getSimpleName() + " " + ((Ordered)o).getOrder());
	}
	
}
