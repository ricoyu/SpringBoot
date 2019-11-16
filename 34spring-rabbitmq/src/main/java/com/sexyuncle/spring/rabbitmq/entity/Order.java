package com.sexyuncle.spring.rabbitmq.entity;

import lombok.Data;

import java.util.Date;

/**
 *  
 * <p>
 * Copyright: Copyright (c) 2019-11-05 18:35
 * <p>
 * Company: Sexy Uncle Inc.
 * <p>
 
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
@Data
public class Order {

    private String orderNo;

    private Date createDt;

    private double payMoney;

    private String userName;
}
