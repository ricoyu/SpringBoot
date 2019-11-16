package com.sexyuncle.spring.rabbitmq.entity;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * Copyright: (C), 2019/11/7 15:54
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Data
public class Order {
	
	private String orderNo;
	
	private Date createDt;
	
	private double payMoney;
	
	private String userName;
}
