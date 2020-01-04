package com.sexyuncle.springboot.json.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * <p>
 * Copyright: (C), 2020/1/4 13:47
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Table(name = "t_pub_params")
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Data
public class PubParams {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false, unique = true, nullable = false)
	private Long id;
	
	@Column(name = "group_name", nullable = false)
	private String groupName;
	
	@Type(type = "json")
	@Column(name = "params")
	private List<Param> params;
	
	@Data
	public static class Param{
		private String paramName;
		
		private Object paramValue;
		
		private String desc;
	}
}
