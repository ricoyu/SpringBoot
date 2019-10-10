package com.sexyuncle.springboot.hikari.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "CUSTOMERS")
@Data
public class Customers implements Serializable {

	private static final long serialVersionUID = 6174304962035725128L;

	@Id
	@Column(name = "customerNumber")
	private Long customerNumber;

	@Column(name = "customerName")
	private String customerName;

	@Column(name = "contactLastName")
	private String contactLastName;

	@Column(name = "contactFirstName")
	private String contactFirstName;

	@Column(name = "phone")
	private String phone;

	@Column(name = "addressLine1")
	private String addressLine1;

	@Column(name = "addressLine2")
	private String addressLine2;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "postalCode")
	private String postalCode;

	@Column(name = "country")
	private String country;

	@Column(name = "salesRepEmployeeNumber")
	private Integer salesRepEmployeeNumber;

	@Column(name = "creditLimit")
	private BigDecimal creditLimit;
}
