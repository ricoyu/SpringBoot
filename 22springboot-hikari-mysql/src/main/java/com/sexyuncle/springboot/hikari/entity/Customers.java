package com.sexyuncle.springboot.hikari.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "CUSTOMERS")
@Data
public class Customers implements Serializable {

	private static final long serialVersionUID = 6174304962035725128L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customerNumber", updatable = false, unique = true, nullable = false)
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
