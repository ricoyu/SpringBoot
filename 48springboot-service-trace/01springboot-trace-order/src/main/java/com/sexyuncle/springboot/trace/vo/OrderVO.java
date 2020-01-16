package com.sexyuncle.springboot.trace.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * Copyright: (C), 2020/1/6 17:00
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Data
public class OrderVO implements Serializable {
	
	private Integer orderId;
	private String order;
	private Integer productId;
	private Integer count;
}
