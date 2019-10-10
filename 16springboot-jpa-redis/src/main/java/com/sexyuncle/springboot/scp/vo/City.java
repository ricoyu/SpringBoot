package com.sexyuncle.springboot.scp.vo;

import java.io.Serializable;

/**
 * 机构列表
 * <p>
 * Copyright: Copyright (c) 2018-05-21 14:49
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
public class City implements Serializable {

	private static final long serialVersionUID = 7941094308661663339L;

	private Long orderId;
	
	private String city;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
