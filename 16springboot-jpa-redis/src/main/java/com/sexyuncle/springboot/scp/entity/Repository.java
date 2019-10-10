package com.sexyuncle.springboot.scp.entity;

import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Cacheable
@Cache(region = "repository", usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "REPOSITORY", schema = "SUPPLY_CHAIN_PORTAL")
public class Repository extends BaseEntity {

	private static final long serialVersionUID = 5381608029999237994L;
	
	@Id
	@GenericGenerator(name = "repository-distributed-identifier",
			strategy = "com.peacefish.orm.commons.identifier.redis.RedisDistributedIdentifier",
			parameters = { 
					@Parameter(name="schema", value="supply_chain_portal"),
					@Parameter(name="table", value="repository"),
					@Parameter(name = "fetch_size", value = "1") })
	@GeneratedValue(generator = "repository-distributed-identifier", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="repository", nullable=false, length=100)
	private String repository;

	@Column(name="ADDRESS", nullable=false, length=200)
	private String address;

	@Column(name="CONTACTOR", nullable=false, length=20)
	private String contactor;

	@Column(name="CONTACT_NUMBER", nullable=false, length=100)
	private String contactNumber;

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
