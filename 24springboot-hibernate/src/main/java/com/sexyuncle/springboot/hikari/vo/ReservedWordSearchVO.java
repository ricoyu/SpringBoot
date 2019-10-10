package com.sexyuncle.springboot.hikari.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReservedWordSearchVO implements Serializable {

	private static final long serialVersionUID = -2088843092976020532L;

	private String reservedWord;

	private String reservedType;
}
