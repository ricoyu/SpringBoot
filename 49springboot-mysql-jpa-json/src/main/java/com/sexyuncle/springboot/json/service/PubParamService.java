package com.sexyuncle.springboot.json.service;

import com.loserico.orm.dao.EntityOperations;
import com.sexyuncle.springboot.json.entity.PubParams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * Copyright: (C), 2020/1/4 14:37
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Service
@Transactional
public class PubParamService {
	
	@Resource
	private EntityOperations entityOperations;
	
	public Long save(PubParams pubParams) {
		entityOperations.save(pubParams);
		return pubParams.getId();
	}
}
