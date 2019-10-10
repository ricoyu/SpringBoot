package com.sexyuncle.springboot.scp.enums;

/**
 * 退货单状态：
 * -1 所有, 1 初始退货单, 234 内部审核, 5 审核通过, 6 审核驳回, 9 等待预约出库, 10 等待目的机构入库, 11 等待退货区收货, 13 等待供应商收货, 17 已报废完成, 18 已提货完成, 20 厂直已完成
 * <p>
 * Copyright: Copyright (c) 2018-05-14 16:55
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
public enum ReturnState{

	INIT(1, "初始退货单"),
	INTERNAL_AUDIT(234, "内部审核"),
	PASS(5, "审核通过"),
	REJECT(6, "审核驳回"),
	TO_DELIVERY(9, "等待预约出库"),
	TO_STORAGE(10, "等待目的机构入库"),
	RETURN_AREA_TAKE_OVER(11, "等待退货区收货"),
	SUPPLIER_TAKE_OVER(13, "等待供应商收货"),
	INVALIDATED(17, "已报废完成"),
	TAKE_DELIVERY(18, "已提货完成"),
	COMPLETE(20, "厂直已完成");
	
    private int code;
    private String desc;
    
	private ReturnState(int code, String desc) {
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
