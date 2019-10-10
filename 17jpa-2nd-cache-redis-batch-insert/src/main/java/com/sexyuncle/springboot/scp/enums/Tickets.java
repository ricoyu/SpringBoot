package com.sexyuncle.springboot.scp.enums;

/**
 * 单据类型
 * 1 新退货、2 采购入库单、3 质保金、4 返点单、5 售后退货
 * <p>
 * Copyright: Copyright (c) 2018-05-14 16:55
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
public enum Tickets {

	NEW_RETURNED(2, "新退货"),
	PURCHASE_ORDER(2, "采购入库单"),
	GUARANTEE_MONEY(3, "质保金"),
	RETURN_ORDER(4, "返点单"),
	SALES_RETURN(5, "售后退货");

	private int code;
	private String desc;

	private Tickets(int code, String desc) {
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
