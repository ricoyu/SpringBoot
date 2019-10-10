package com.sexyuncle.springboot.scp.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 结算单审核状态
 * 101 待审核、 102 审核中、103 审核通过、104 审核驳回
 * <p>
 * Copyright: Copyright (c) 2018-05-14 16:55
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
public enum SettlementAuditState{

	TO_AUDIT(101, "待审核"),
	AUDITTING(102, "审核中"),
	PASS(103, "审核通过"),
	REJECT(104, "审核驳回");
	
    private int code;
    private String desc;
    
	private SettlementAuditState(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@JsonValue
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
