package com.sexyuncle.springboot.scp.jpa.converter;

import com.loserico.common.lang.utils.MathUtils;
import com.sexyuncle.springboot.scp.enums.ConfirmStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;

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
		logger.error("注意从数据库中取出的值{},在枚举类型Status中找不到对应的值!", dbData);
		return null;
	}

}
