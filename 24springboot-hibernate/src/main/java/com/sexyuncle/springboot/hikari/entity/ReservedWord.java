package com.sexyuncle.springboot.hikari.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="reserved_words")
@Data
public class ReservedWord implements Serializable{

	private static final long serialVersionUID = -4983438668263350868L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", updatable=false, unique=true, nullable=false)
	private Long id;
	
	@Column(name="reserved_word")
	private String reservedWord;
	
	@Column(name="reserved_type")
	private String reservedType;
	
	@Column(name="note")
	private String note;
}
