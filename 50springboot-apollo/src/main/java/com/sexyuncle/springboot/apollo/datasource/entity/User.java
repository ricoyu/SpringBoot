package com.sexyuncle.springboot.apollo.datasource.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * <p>
 * Copyright: (C), 2020/1/16 9:58
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Entity
@Data
public class User {
	
	@Id
	private Integer id;
	
	private String name;
}
