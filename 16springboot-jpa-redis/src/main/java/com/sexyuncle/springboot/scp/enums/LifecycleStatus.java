package com.sexyuncle.springboot.scp.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 结算单状态, 0 源数据 1 草稿 2 新数据
 * <p>
 * Copyright: Copyright (c) 2018-05-14 16:55
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
public enum LifecycleStatus{

	SOURCE(0, "源数据"),
	DRAFT(1, "草稿"),
	NEW(2, "新数据");
	
    private int code;
    private String desc;
    
	private LifecycleStatus(int code, String desc) {
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
