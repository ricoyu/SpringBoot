package com.sexyuncle.springboot.scp.vo;

import java.io.Serializable;

public class DeliverCentreVO implements Serializable {

	private static final long serialVersionUID = 5399543647980802823L;

	private Long deliverCenterId;
	
	private String deliverCenterName;
	
	private Long storeId;
	
	private String storeName;

	public Long getDeliverCenterId() {
		return deliverCenterId;
	}

	public void setDeliverCenterId(Long deliverCenterId) {
		this.deliverCenterId = deliverCenterId;
	}

	public String getDeliverCenterName() {
		return deliverCenterName;
	}

	public void setDeliverCenterName(String deliverCenterName) {
		this.deliverCenterName = deliverCenterName;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	
}
