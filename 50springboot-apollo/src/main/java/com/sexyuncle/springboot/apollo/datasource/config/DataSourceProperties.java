package com.sexyuncle.springboot.apollo.datasource.config;

import com.ctrip.framework.apollo.enums.PropertyChangeType;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.loserico.common.lang.utils.ReflectionUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * <p>
 * Copyright: (C), 2020/1/16 15:18
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Data
@Slf4j
public class DataSourceProperties {
	
	private String url;
	
	@ApolloConfigChangeListener({"db"})
	public void onChange(ConfigChangeEvent changeEvent) {
		Set<String> changedKeys = changeEvent.changedKeys();
		for (String key : changedKeys) {
			ConfigChange change = changeEvent.getChange(key);
			PropertyChangeType changeType = change.getChangeType();
			log.info("Found change - key: {}, oldValue: {}, newValue: {}, changeType: {}",
					change.getPropertyName(),
					change.getOldValue(),
					change.getNewValue(),
					changeType);
			
			String propertyName = change.getPropertyName();
			propertyName = propertyName.replaceAll("spring.datasource.", "");
			
			switch (changeType) {
				case DELETED:
					ReflectionUtils.setField(propertyName, this, null);
					break;
				default:
					ReflectionUtils.setField(propertyName, this, change.getNewValue());
			}
		}
	}
}
