package com.sexyuncle.springboot.scp.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 付款状态(这个code是自己定的，其他状态都是取得JD)
 * 1 未付款、2已付款、3 已开票
 * <p>
 * Copyright: Copyright (c) 2018-05-14 16:55
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
public enum PayState{

	NOT_PAID(2, "未付款"),
	PAID(4, "已付款"),
	INVOICED(4, "已开票");
	
    private int code;
    private String desc;
    
	private PayState(int code, String desc) {
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
