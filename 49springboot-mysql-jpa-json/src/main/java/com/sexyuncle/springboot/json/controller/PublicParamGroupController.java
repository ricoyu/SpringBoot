package com.sexyuncle.springboot.json.controller;

import com.loserico.json.jackson.JacksonUtils;
import com.sexyuncle.springboot.json.entity.PubParams;
import com.sexyuncle.springboot.json.service.PubParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * Copyright: (C), 2020/1/4 14:14
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Api("公共参数管理")
@RestController
@Slf4j
public class PublicParamGroupController {
	
	@Resource
	private PubParamService pubParamService;
	
	/**
	 * 前端传的是JSON格式数据:
	 * {
	 *   "groupName": "默认分组1",
	 *   "id": 1,
	 *   "params": [
	 *     {
	 *       "paramName": "storeId",
	 *       "paramValue": 1111,
	 *       "desc": "店铺ID"
	 *     },
	 *     {
	 *       "paramName": "storeName",
	 *       "paramValue": "南华小馆",
	 *       "desc": "店铺名称"
	 *     }
	 *   ]
	 * }
	 * @param pubParams
	 * @return
	 */
	@ApiOperation(value="创建公共参数", consumes = "application/json")
	@PostMapping(value = "/pub-param-group", consumes = "application/json")
	public Long createPublicParamGroup(@RequestBody PubParams pubParams) {
		log.info(JacksonUtils.toJson(pubParams));
		return pubParamService.save(pubParams);
	}
}
