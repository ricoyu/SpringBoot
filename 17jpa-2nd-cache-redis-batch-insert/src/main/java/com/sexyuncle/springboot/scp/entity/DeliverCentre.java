package com.sexyuncle.springboot.scp.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Cacheable(true)
@Cache(region="deliver_centre", usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "DELIVER_CENTRE", schema = "SUPPLY_CHAIN_PORTAL")
@Access(AccessType.FIELD)
public class DeliverCentre extends BaseEntity {

	private static final long serialVersionUID = -6938028451678106911L;
	
	@Id
	@GenericGenerator(name = "deliver_centre-distributed-identifier",
			strategy = "com.peacefish.orm.commons.identifier.redis.RedisDistributedIdentifier",
			parameters = { 
					@Parameter(name="schema", value="supply_chain_portal"),
					@Parameter(name="table", value="deliver_centre"),
					@Parameter(name = "fetch_size", value = "1") })
	@GeneratedValue(generator = "deliver_centre-distributed-identifier", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "CENTRE_ID", nullable = false)
	private Long centreId;

	@Column(name = "CENTRE_NAME", nullable = false, length = 100)
	private String centreName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getCentreId() {
		return centreId;
	}

	public void setCentreId(Long centreId) {
		this.centreId = centreId;
	}

	public String getCentreName() {
		return centreName;
	}

	public void setCentreName(String centreName) {
		this.centreName = centreName;
	}

}
