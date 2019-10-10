package com.sexyuncle.springboot.hikari.mybatis.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Customers implements Serializable {

	private static final long serialVersionUID = 6174304962035725128L;

	private Long customerNumber;

	private String customerName;

	private String contactLastName;

	private String contactFirstName;

	private String phone;

	private String addressLine1;

	private String addressLine2;

	private String city;

	private String state;

	private String postalCode;

	private String country;

	private Integer salesRepEmployeeNumber;

	private BigDecimal creditLimit;
}
