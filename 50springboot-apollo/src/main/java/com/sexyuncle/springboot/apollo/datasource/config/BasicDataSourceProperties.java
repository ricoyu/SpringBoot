package com.sexyuncle.springboot.apollo.datasource.config;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.loserico.common.lang.utils.BeanUtils;
import com.loserico.common.lang.utils.ReflectionUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.Set;

/**
 * <p>
 * Copyright: (C), 2020/1/16 17:09
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "datasource")
@Data
@Slf4j
public class BasicDataSourceProperties {
	
	/*
	 * BasicDataSource构建完之后set进来, 
	 * 属性有更新时在
	 */
	private volatile BasicDataSource dataSourceInstance;
	
	/*
	 * Apollo中datasource namespace的配置示例
	 * datasource.driverClassName=com.mysql.jdbc.Driver
	 * datasource.url=jdbc:mysql://192.168.1.214:3306/zl_dcms?useUnicode=true&amp;characterEncoding=UTF-8&amp;autoReconnect=true&amp;failOverReadOnly=false
	 * datasource.username=devlop
	 * datasource.password=devlop
	 * datasource.initialSize=5
	 * datasource.maxActive=200
	 * datasource.maxIdle=30
	 * datasource.minIdle=2
	 * datasource.poolPreparedStatements=true
	 * datasource.removeAbandoned=true
	 * datasource.removeAbandonedTimeout=180
	 * datasource.maxWait=3000
	 * datasource.timeBetweenEvictionRunsMillis=3600000
	 * datasource.numTestsPerEvictionRun=10
	 * datasource.minEvictableIdleTimeMillis=7200000
	 * datasource.validationQuery=select 1
	 * datasource.testOnBorrow=true
	 * datasource.testOnReturn=true
	 * datasource.testWhileIdle=true
	 */
	
	private String driverClassName;
	
	private String url;
	
	private String username;
	
	private String password;
	
	private int initialSize;
	
	private int maxActive;
	
	private int maxIdle;
	
	private int minIdle;
	
	private boolean poolPreparedStatements;
	
	private boolean removeAbandoned;
	
	private int removeAbandonedTimeout;
	
	private int maxWait;
	
	private int timeBetweenEvictionRunsMillis;
	
	private int numTestsPerEvictionRun;
	
	private int minEvictableIdleTimeMillis;
	
	private String validationQuery;
	
	private boolean testOnBorrow;
	
	private boolean testOnReturn;
	
	private boolean testWhileIdle;
	
	@ApolloConfigChangeListener({"datasource"})
	public void onChange(ConfigChangeEvent changeEvent) {
		Set<String> changedKeys = changeEvent.changedKeys();
		for (String key : changedKeys) {
			ConfigChange change = changeEvent.getChange(key);
			log.info("Found change - key: {}, oldValue: {}, newValue: {}, changeType: {}",
					change.getPropertyName(),
					change.getOldValue(),
					change.getNewValue(),
					change.getChangeType());
			
			String propertyName = change.getPropertyName();
			propertyName = propertyName.replaceAll("datasource.", "");
			ReflectionUtils.setField(propertyName, this, change.getNewValue());
		}
		
		log.info("开始重新初始化DataSource");
		BeanUtils.copyProperties(this, dataSourceInstance);
		reinitializeDataSource();
	}
	
	private void reinitializeDataSource() {
		try {
			log.info("先把原来的连接池关掉");
			dataSourceInstance.close();
		} catch (SQLException e) {
			log.error("", e);
		}
		log.info("再用新属性值恢复成初始状态");
		ReflectionUtils.setField("closed", dataSourceInstance, false);
	}
}
