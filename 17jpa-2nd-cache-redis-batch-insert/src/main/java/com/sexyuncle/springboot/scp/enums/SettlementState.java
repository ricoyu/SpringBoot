package com.sexyuncle.springboot.scp.enums;

/**
 * 结算单状态(确认状态)
 * <p>
 * Copyright: Copyright (c) 2018-05-14 16:55
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
public enum SettlementState{

	CONFIRM(2, "供应商已确认"),
	REJECT(4, "系统驳回");
	
    private int code;
    private String desc;
    
	private SettlementState(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
