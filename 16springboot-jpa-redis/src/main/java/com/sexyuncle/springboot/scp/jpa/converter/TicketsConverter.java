package com.sexyuncle.springboot.scp.jpa.converter;

import java.util.EnumSet;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.loserico.commons.utils.MathUtils;
import com.sexyuncle.springboot.scp.enums.Tickets;

/**
 * JD 业务单号转换器
 * <p>
 * Copyright: Copyright (c) 2018-05-21 14:27
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
public class TicketsConverter implements AttributeConverter<Tickets, Integer> {

	private static final Logger logger = LoggerFactory.getLogger(TicketsConverter.class);

	@Override
	public Integer convertToDatabaseColumn(Tickets ticket) {
		return ticket == null ? null : ticket.getCode();
	}

	@Override
	public Tickets convertToEntityAttribute(Integer dbData) {
		EnumSet<Tickets> set = EnumSet.allOf(Tickets.class);
		for (Tickets ticket : set) {
			if (MathUtils.equals(dbData, ticket.getCode())) {
				return ticket;
			}
		}
		logger.error("注意从数据库中取出的值{},在枚举类型Tickets中找不到对应的值!", dbData);
		return null;
	}

}
