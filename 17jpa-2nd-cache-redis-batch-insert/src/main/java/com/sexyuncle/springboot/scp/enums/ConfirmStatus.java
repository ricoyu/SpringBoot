package com.sexyuncle.springboot.scp.enums;

/**
 * 回告状态
 * 
 * 0 未回告 1 回告成功 2 回告中 3 回告失败，导出的都是未回告，所以默认0
 * <p>
 * Copyright: Copyright (c) 2018-05-14 17:00
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
public enum ConfirmStatus {

	NO(0, "未回告"),
	SUCCESS(1, "回告成功"),
	CONFIRMING(2, "回告中"),
	FAIL(3, "回告失败");

	private int code;
	private String desc;

	private ConfirmStatus(int code, String desc) {
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
