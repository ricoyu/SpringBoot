package com.sexyuncle.springboot.scp.service;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loserico.cache.redis.JedisUtils;
import com.loserico.cache.redis.collection.ConcurrentMap;
import com.loserico.orm.dao.SQLOperations;
import com.sexyuncle.springboot.scp.vo.City;

/**
 * 分配/退货 机构
 * <p>
 * Copyright: Copyright (c) 2018-05-21 14:33
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@Service
@Transactional
public class CityService {
	
	@Autowired
	private SQLOperations sqlOperations;
	
	/**
	 * 将订单中所有机构放入缓存，其实就是城市列表
	 */
//	@PostInitialize
	public void cacheCities() {
		List<City> cities = sqlOperations.namedSqlQuery("findAllCities", City.class);
		Map<Long, String> cityMap = cities.stream().collect(toMap(City::getOrderId, City::getCity));
	}
}
