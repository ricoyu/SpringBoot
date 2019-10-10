package com.sexyuncle.springboot.scp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Cache(region="return_item", usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "RETURN_ITEM", schema = "SUPPLY_CHAIN_PORTAL")
@Access(AccessType.FIELD)
public class ReturnItem extends BaseEntity {

	private static final long serialVersionUID = 7544354450036813300L;
	
	@Id
	@GenericGenerator(name = "return_item-distributed-identifier",
			strategy = "com.peacefish.orm.commons.identifier.redis.RedisDistributedIdentifier",
			parameters = { 
					@Parameter(name="schema", value="supply_chain_portal"),
					@Parameter(name="table", value="return_item"),
					@Parameter(name = "fetch_size", value = "10") })
	@GeneratedValue(generator = "return_item-distributed-identifier", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="RETURN_ID", nullable=false)
	private Long returnId;
	
	@Column(name = "ORDER_ID", nullable = false)
	private Long orderId; //JD 订单号

	@Column(name = "CITY", nullable = false, length = 500)
	private String city; //分配机构，西安、上海等
	
	@Column(name = "SKU_CODE", nullable = false)
	private Long skuCode; //JD 商品编码

	@Column(name = "SKU_NAME", length = 200, nullable = false)
	private String skuName; //商品名称
	
	@Column(name = "QTY")
	private long qty; //申请数量
	
	@Column(name = "ACTUAL_QTY", nullable = false)
	private long actualQty; //实退数量

	@Column(name = "UNIT_PRICE", nullable = false)
	private BigDecimal unitPrice; //退货价格

	@Column(name = "AMOUNT")
	private BigDecimal amount; //退货金额

	@Column(name = "APPLY_DATE", columnDefinition = "DATETIME", nullable = false, length = 19)
	private LocalDateTime applyDate; //退货申请日期

	@Column(name = "REMARK")
	private String remark; //备注

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

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public long getQty() {
		return qty;
	}

	public void setQty(long qty) {
		this.qty = qty;
	}

	public long getActualQty() {
		return actualQty;
	}

	public void setActualQty(long actualQty) {
		this.actualQty = actualQty;
	}


	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(LocalDateTime applyDate) {
		this.applyDate = applyDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getReturnId() {
		return returnId;
	}

	public void setReturnId(Long returnId) {
		this.returnId = returnId;
	}

}
