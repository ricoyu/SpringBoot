package com.sexyuncle.springboot.scp.jpa.converter;

import java.util.EnumSet;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.loserico.commons.utils.MathUtils;
import com.sexyuncle.springboot.scp.enums.PayState;

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
public class PayStateConverter implements AttributeConverter<PayState, Integer> {

	private static final Logger logger = LoggerFactory.getLogger(PayStateConverter.class);

	@Override
	public Integer convertToDatabaseColumn(PayState payState) {
		return payState == null ? null : payState.getCode();
	}

	@Override
	public PayState convertToEntityAttribute(Integer dbData) {
		EnumSet<PayState> set = EnumSet.allOf(PayState.class);
		for (PayState payState : set) {
			if (MathUtils.equals(dbData, payState.getCode())) {
				return payState;
			}
		}
		logger.error("注意从数据库中取出的值{},在枚举类型PayState中找不到对应的值!", dbData);
		return null;
	}

}
