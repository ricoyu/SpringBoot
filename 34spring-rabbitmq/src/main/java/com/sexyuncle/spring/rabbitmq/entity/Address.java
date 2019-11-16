package com.sexyuncle.spring.rabbitmq.entity;

import lombok.Data;

/**
 *  
 * <p>
 * Copyright: Copyright (c) 2019-11-05 18:36
 * <p>
 * Company: Sexy Uncle Inc.
 * <p>
 
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
@Data
public class Address {

    private String provinces;

    private String city;

    private String area;
}
