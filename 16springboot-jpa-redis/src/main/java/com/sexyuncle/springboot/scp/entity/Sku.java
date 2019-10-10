package com.sexyuncle.springboot.scp.entity;

import java.util.Objects;

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
@Cache(region="sku", usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "SKU", schema = "SUPPLY_CHAIN_PORTAL")
@Access(AccessType.FIELD)
public class Sku extends BaseEntity{

	private static final long serialVersionUID = -1012231095716165393L;
	
	@Id
	@GenericGenerator(name = "sku-distributed-identifier",
			strategy = "com.peacefish.orm.commons.identifier.redis.RedisDistributedIdentifier",
			parameters = { 
					@Parameter(name="schema", value="supply_chain_portal"),
					@Parameter(name="table", value="sku"),
					@Parameter(name = "fetch_size", value = "1") })
	@GeneratedValue(generator = "sku-distributed-identifier", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "SKU_CODE", nullable = false)
	private Long skuCode; //JD 商品编码

	@Column(name = "SKU_NAME", nullable = false, length = 200)
	private String skuName; //商品名称

	public Long getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(Long skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Sku)) {
			return false;
		}
		Sku sku = (Sku) o;
		return Objects.equals(skuCode, sku.getSkuCode());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(skuCode);
	}
}
