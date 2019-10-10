package com.sexyuncle.springboot.scp.jpa.converter;

import java.util.EnumSet;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.loserico.commons.utils.MathUtils;
import com.sexyuncle.springboot.scp.enums.SettlementState;

public class SettlementStateConverter implements AttributeConverter<SettlementState, Integer> {

	private static final Logger logger = LoggerFactory.getLogger(SettlementStateConverter.class);

	@Override
	public Integer convertToDatabaseColumn(SettlementState settlementState) {
		return settlementState == null ? null : settlementState.getCode();
	}

	@Override
	public SettlementState convertToEntityAttribute(Integer dbData) {
		EnumSet<SettlementState> set = EnumSet.allOf(SettlementState.class);
		for (SettlementState settlementState : set) {
			if (MathUtils.equals(dbData, settlementState.getCode())) {
				return settlementState;
			}
		}
		logger.error("注意从数据库中取出的值{},在枚举类型SettlementState中找不到对应的值!", dbData);
		return null;
	}

}
