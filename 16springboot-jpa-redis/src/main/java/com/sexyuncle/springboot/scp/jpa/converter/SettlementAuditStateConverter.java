package com.sexyuncle.springboot.scp.jpa.converter;

import java.util.EnumSet;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.loserico.commons.utils.MathUtils;
import com.sexyuncle.springboot.scp.enums.SettlementAuditState;

public class SettlementAuditStateConverter implements AttributeConverter<SettlementAuditState, Integer> {

	private static final Logger logger = LoggerFactory.getLogger(SettlementAuditStateConverter.class);

	@Override
	public Integer convertToDatabaseColumn(SettlementAuditState settlementAuditState) {
		return settlementAuditState == null ? null : settlementAuditState.getCode();
	}

	@Override
	public SettlementAuditState convertToEntityAttribute(Integer dbData) {
		EnumSet<SettlementAuditState> set = EnumSet.allOf(SettlementAuditState.class);
		for (SettlementAuditState settlementAuditState : set) {
			if (MathUtils.equals(dbData, settlementAuditState.getCode())) {
				return settlementAuditState;
			}
		}
		logger.error("注意从数据库中取出的值{},在枚举类型SettlementAuditState中找不到对应的值!", dbData);
		return null;
	}

}
