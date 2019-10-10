package com.sexyuncle.springboot.scp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Entity父类，提供creator、createTime、modifier、modifyTime通用字段自动填值
 * 
 * @author Rico Yu ricoyu520@gmail.com
 * @since 2017-05-23 11:22
 * @version 1.0
 *
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -7833247830642842225L;

	private String creator;

	private LocalDateTime createTime;

	private String modifier;

	private LocalDateTime modifyTime;

	@Column(name = "CREATOR", length = 100, nullable = false)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	/*
	 * 默认映射的数据库字段类型为TIMESTAMP,改为DATETIME
	 */
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME", nullable = false, length = 19)
	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	@Column(name = "MODIFIER", length = 100, nullable = false)
	public String getModifier() {
		return modifier;
	}

	public void setModifier(String lastModifier) {
		this.modifier = lastModifier;
	}

	@Column(name = "MODIFY_TIME", columnDefinition = "DATETIME", nullable = false, length = 19)
	public LocalDateTime getModifyTime() {
		return modifyTime;

	}

	public void setModifyTime(LocalDateTime modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 在Entity被持久化之前做一些操作
	 */
	@PrePersist
	protected void onPrePersist() {
		LocalDateTime now = LocalDateTime.now();
		setCreateTime(now);
		setModifyTime(now);
		setCreator("system");
		setModifier("system");
	}

	@PreUpdate
	protected void onPreUpdate() {
		setModifyTime(LocalDateTime.now());
		setModifier("system");
	}
}