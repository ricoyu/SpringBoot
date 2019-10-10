package com.sexyuncle.springboot.scp.entity;

import java.math.BigDecimal;

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
@Cache(region="order_item", usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ORDER_ITEM", schema = "SUPPLY_CHAIN_PORTAL")
@Access(AccessType.FIELD)
public class OrderItem extends BaseEntity {

	private static final long serialVersionUID = 7544354450036813300L;
	
	@Id
	@GenericGenerator(name = "order_item-distributed-identifier",
			strategy = "com.peacefish.orm.commons.identifier.redis.RedisDistributedIdentifier",
			parameters = { 
					@Parameter(name="schema", value="supply_chain_portal"),
					@Parameter(name="table", value="order_item"),
					@Parameter(name = "fetch_size", value = "1") })
	@GeneratedValue(generator = "order_item-distributed-identifier", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "SKU_CODE", nullable = false)
	private Long skuCode; //JD 商品编码

	@Column(name = "SKU_NAME", length = 200, nullable = false)
	private String skuName; //商品名称
	
	@Column(name = "ORDER_ID", nullable = false)
	private Long orderId; //JD 订单号

	@Column(name = "UNIT_PRICE", nullable = false)
	private BigDecimal unitPrice; //采购价格

	@Column(name = "QTY")
	private long qty; //采购数量

	@Column(name = "ACTUAL_QTY", nullable = false)
	private long actualQty; //实收数量

	@Column(name = "JD_TAG_COUNT")
	private long jdTagCount; //京东贴码数量

	@Column(name = "DUP_UPC", length = 20)
	private String dupUpc; //重码UPC

	@Column(name = "UPC", length = 20)
	private String upc; //UPC码, 类似7368081

	@Column(name = "AMOUNT")
	private BigDecimal amount; //采购金额

	@Column(name = "ACTUAL_AMOUNT")
	private BigDecimal actualAmount; //实际金额

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

	public long getJdTagCount() {
		return jdTagCount;
	}

	public void setJdTagCount(long jdTagCount) {
		this.jdTagCount = jdTagCount;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getDupUpc() {
		return dupUpc;
	}

	public void setDupUpc(String dupUpc) {
		this.dupUpc = dupUpc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
