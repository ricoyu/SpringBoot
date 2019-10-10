package com.sexyuncle.springboot.scp.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 订单状态：0 新采购单 2 等待入库 3 已完成 4 部分收货 6 待审核 7 审核不通过 8 等待签收 15 待确认 17 下传中 18 下传失败 20 供应商驳回 21 等待下传EDI，京东后台查询订单状态只有选全部可以查到，所以设为NULLABLE
 * <p>
 * Copyright: Copyright (c) 2018-05-14 16:55
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
public enum OrderState {

	NEW(0, "新采购单"),
	TO_STORAGE(2, "等待入库"),
	COMPLETE(3, "已完成"),
	PARTIAL_DELIVERY(4, "部分收货"),
	TO_AUDIT(6, "待审核"),
	AUDIT_FAIL(7, "审核不通过"),
	TO_CONFIRM(15, "待确认"),
	DOWNLOADING(17, "下传中"),
	DOWNLOAD_FAIL(18, "下传失败"),
	SUPPLIER_REJECT(20, "供应商驳回"),
	DOWNLOADING_EDI(21, "等待下传EDI");
	
    private int code;
    private String desc;
    
	private OrderState(int code, String desc) {
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
