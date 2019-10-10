package com.sexyuncle.springboot.scp.entity;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.sexyuncle.springboot.scp.enums.ReturnState;
import com.sexyuncle.springboot.scp.jpa.converter.ReturnStateConverter;

@Entity
@Cacheable(true)
@Cache(region = "supply_chain", usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "PURCHASE_RETURN", schema = "SUPPLY_CHAIN_PORTAL")
@Access(AccessType.FIELD)
public class PurchaseReturn extends BaseEntity {

	private static final long serialVersionUID = 1241935609763109311L;

	@Id
	@GenericGenerator(name = "purchase_return-distributed-identifier",
			strategy = "com.peacefish.orm.commons.identifier.redis.RedisDistributedIdentifier",
			parameters = {
					@Parameter(name = "schema", value = "supply_chain_portal"),
					@Parameter(name = "table", value = "purchase_return"),
					@Parameter(name = "fetch_size", value = "10") })
	@GeneratedValue(generator = "purchase_return-distributed-identifier", strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "RETURN_ID", nullable = false)
	private Long returnId; //JD 京东退货单号

	@Column(name = "STATE")
	@Convert(converter = ReturnStateConverter.class)
	private ReturnState state;
	
	@Column(name = "SUPPLIER_CODE", nullable = false, length = 20)
	private String supplierCode; //供应商简码

	@Column(name = "SUPPLIER", nullable = false, length = 200)
	private String supplier;//供应商

	@Column(name = "DELETED", nullable = false)
	private boolean deleted; //删除状态

	public ReturnState getState() {
		return state;
	}

	public void setState(ReturnState state) {
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReturnId() {
		return returnId;
	}

	public void setReturnId(Long returnId) {
		this.returnId = returnId;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		return Objects.hash(returnId);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof PurchaseReturn)) {
			return false;
		}
		PurchaseReturn purchaseReturn = (PurchaseReturn) o;
		return Objects.equals(returnId, purchaseReturn.getReturnId());
	}
	
}
