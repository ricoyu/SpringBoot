package com.sexyuncle.springboot.scp.vo;

import java.io.Serializable;

public class PurchaseOrderPurchaserVO implements Serializable {

	private static final long serialVersionUID = 8083980133330624599L;
	private Long orderId; //JD 订单号
	private String purchaser; //采购员

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}

}
