package com.sexyuncle.springboot.scp.jpa.converter;

import java.util.EnumSet;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.loserico.commons.utils.MathUtils;
import com.sexyuncle.springboot.scp.enums.OrderState;

public class OrderStateConverter implements AttributeConverter<OrderState, Integer> {

	private static final Logger logger = LoggerFactory.getLogger(OrderStateConverter.class);

	@Override
	public Integer convertToDatabaseColumn(OrderState orderState) {
		return orderState == null ? null : orderState.getCode();
	}

	@Override
	public OrderState convertToEntityAttribute(Integer dbData) {
		EnumSet<OrderState> set = EnumSet.allOf(OrderState.class);
		for (OrderState orderState : set) {
			if (MathUtils.equals(dbData, orderState.getCode())) {
				return orderState;
			}
		}
		logger.error("注意从数据库中取出的值{},在枚举类型Status中找不到对应的值!", dbData);
		return null;
	}

}
