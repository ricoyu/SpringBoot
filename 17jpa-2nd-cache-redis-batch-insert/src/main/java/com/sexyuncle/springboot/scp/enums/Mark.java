package com.sexyuncle.springboot.scp.enums;

/**
 * 贴码建议
 * 
 * 贴码建议 1 需要贴码 0 不需要贴码 查询到的都是没有值的，所以设为NULLABLE
 * <p>
 * Copyright: Copyright (c) 2018-05-14 16:55
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
public enum Mark {

	NO_MARK(0, "不需要贴码"),
	MARK(1, "需要贴码");

	private int code;

	private String desc;

	private Mark(int code, String desc) {
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
