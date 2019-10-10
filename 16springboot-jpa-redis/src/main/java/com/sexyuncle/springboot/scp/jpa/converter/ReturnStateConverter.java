package com.sexyuncle.springboot.scp.jpa.converter;

import java.util.EnumSet;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.loserico.commons.utils.MathUtils;
import com.sexyuncle.springboot.scp.enums.ReturnState;

/**
 * 退货状态转换
 * <p>
 * Copyright: Copyright (c) 2018-05-21 14:27
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
public class ReturnStateConverter implements AttributeConverter<ReturnState, Integer> {

	private static final Logger logger = LoggerFactory.getLogger(ReturnStateConverter.class);

	@Override
	public Integer convertToDatabaseColumn(ReturnState returnState) {
		return returnState == null ? null : returnState.getCode();
	}

	@Override
	public ReturnState convertToEntityAttribute(Integer dbData) {
		EnumSet<ReturnState> set = EnumSet.allOf(ReturnState.class);
		for (ReturnState returnState : set) {
			if (MathUtils.equals(dbData, returnState.getCode())) {
				return returnState;
			}
		}
		logger.error("注意从数据库中取出的值{},在枚举类型ReturnState中找不到对应的值!", dbData);
		return null;
	}

}
