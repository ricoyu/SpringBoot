package com.sexyuncle.springboot.apollo.datasource;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.loserico.common.lang.utils.IOUtils;
import com.loserico.orm.dao.EntityOperations;
import com.sexyuncle.springboot.apollo.datasource.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * 数据源配置放到阿波罗, 在阿波罗修改数据源配置实时应用更改
 * <p>
 * Copyright: Copyright (c) 2019-02-16 21:38
 * <p>
 * Company: DataSense
 * <p>
 *
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
@EnableApolloConfig
@SpringBootApplication
public class ApolloApplication implements CommandLineRunner {
	
	@Resource
	private EntityOperations entityOperations;
	
	public static void main(String[] args) {
		SpringApplication.run(ApolloApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		IOUtils.readCommandLine((s) -> {
			System.out.println(entityOperations.get(User.class, 1).getName());
		});
	}
}
