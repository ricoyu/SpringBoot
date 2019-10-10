package com.sexyuncle.springboot.scp.jpa.converter;

import java.util.EnumSet;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.loserico.commons.utils.MathUtils;
import com.sexyuncle.springboot.scp.enums.LifecycleStatus;

public class LifecycleStatusConverter implements AttributeConverter<LifecycleStatus, Integer> {

	private static final Logger logger = LoggerFactory.getLogger(LifecycleStatusConverter.class);

	@Override
	public Integer convertToDatabaseColumn(LifecycleStatus lifecycleStatus) {
		return lifecycleStatus == null ? null : lifecycleStatus.getCode();
	}

	@Override
	public LifecycleStatus convertToEntityAttribute(Integer dbData) {
		EnumSet<LifecycleStatus> set = EnumSet.allOf(LifecycleStatus.class);
		for (LifecycleStatus lifecycleStatus : set) {
			if (MathUtils.equals(dbData, lifecycleStatus.getCode())) {
				return lifecycleStatus;
			}
		}
		logger.error("注意从数据库中取出的值{},在枚举类型LifecycleStatus中找不到对应的值!", dbData);
		return null;
	}

}
