package com.loserico.rocketmq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class OrderPaymentDto {
    
	private long orderid;
	
	private BigDecimal paymoney;
}