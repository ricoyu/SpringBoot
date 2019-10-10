package com.sexyuncle.springboot.druid.mybatis.entity;

import java.time.LocalDateTime;

import lombok.Data;

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
@Data
public class Ticket {

	private Long id;

	// 运单号
	private String orderNo;

	// 问题件类型编码, gd_ticket_type主键值
	private Long typeCode;

	// 情况说明
	private String desc;

	// 登记站点编码
	private String issueSite;

	// 登记人工号
	private String issueStaffNo;

	// 接收站站点编码, 如果是无头件(未认领), 则没有接受站点
	private String targetSite;

	// 处理人工号
	private String targetStaffNo;

	// 问题件来源 TICKET_ORIGIN表主键
	private Long originId;

	// 1-当前轮到发布人处理 2-当前轮到接收人处理
	private int respondType;

	// 问题件状态, 0-初始状态 1-处理中 2-已结案
	private int status;

	// 问题件附件
	private String orderPic;

	// 该问题件上次处理时间, 比如最近一次回复的时间
	private LocalDateTime respondTime;

	// 巴枪扫描或发布时间
	private LocalDateTime createTime;

	// 更新时间
	private LocalDateTime updateTime;
}