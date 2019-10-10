package com.sexyuncle.springboot.scp.jpa.converter;

import java.util.EnumSet;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.loserico.commons.utils.MathUtils;
import com.sexyuncle.springboot.scp.enums.ConfirmStatus;

public class ConfirmStatusConverter implements AttributeConverter<ConfirmStatus, Integer> {

	private static final Logger logger = LoggerFactory.getLogger(ConfirmStatusConverter.class);

	@Override
	public Integer convertToDatabaseColumn(ConfirmStatus confirmStatus) {
		return confirmStatus == null ? null : confirmStatus.getCode();
	}

	@Override
	public ConfirmStatus convertToEntityAttribute(Integer dbData) {
		EnumSet<ConfirmStatus> set = EnumSet.allOf(ConfirmStatus.class);
		for (ConfirmStatus confirmStatus : set) {
			if (MathUtils.equals(dbData, confirmStatus.getCode())) {
				return confirmStatus;
			}
		}
		logger.error("注意从数据库中取出的值{},在枚举类型ReturnState中找不到对应的值!", dbData);
		return null;
	}

}
