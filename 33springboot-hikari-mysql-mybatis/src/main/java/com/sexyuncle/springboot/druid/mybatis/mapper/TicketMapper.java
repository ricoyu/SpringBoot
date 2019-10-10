package com.sexyuncle.springboot.druid.mybatis.mapper;

import com.sexyuncle.springboot.druid.mybatis.entity.Ticket;
import org.apache.ibatis.annotations.Mapper;

/**
 * 问题件
 * <p>
 * Copyright: Copyright (c) 2019-10-09 11:31
 * <p>
 * Company: Sexy Uncle Inc.
 * <p>
 *
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@Mapper
public interface TicketMapper {

	Ticket findOne();
}
