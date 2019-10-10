package com.sexyuncle.springboot.scp.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.sexyuncle.springboot.scp.enums.ConfirmStatus;
import com.sexyuncle.springboot.scp.enums.Mark;
import com.sexyuncle.springboot.scp.enums.OrderState;
import com.sexyuncle.springboot.scp.jpa.converter.ConfirmStatusConverter;
import com.sexyuncle.springboot.scp.jpa.converter.OrderStateConverter;

@Entity
@Cacheable(true)
@Cache(region = "supply_chain", usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "PURCHASE_ORDER", schema = "SUPPLY_CHAIN_PORTAL")
@Access(AccessType.FIELD)
public class PurchaseOrder extends BaseEntity {

	private static final long serialVersionUID = -17336191777507884L;

	@Id
	@GenericGenerator(name = "purchase_order-distributed-identifier",
			strategy = "com.peacefish.orm.commons.identifier.redis.RedisDistributedIdentifier",
			parameters = {
					@Parameter(name = "schema", value = "supply_chain_portal"),
					@Parameter(name = "table", value = "purchase_order"),
					@Parameter(name = "fetch_size", value = "50") })
	@GeneratedValue(generator = "purchase_order-distributed-identifier", strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "ORDER_ID", nullable = false)
	private Long orderId; //JD 订单号

	@Column(name = "SUPPLIER_CODE", nullable = false, length = 20)
	private String supplierCode; //供应商简码

	@Column(name = "SUPPLIER", nullable = false, length = 200)
	private String supplier;//供应商

	@Column(name = "PROPERTIES", length = 200)
	private String properties; //订单属性

	@Column(name = "STATE")
	@Convert(converter = OrderStateConverter.class)
	private OrderState state; //订单状态：0 新采购单 2 等待入库 3 已完成 4 部分收货 6 待审核 7 审核不通过 8 等待签收 15 待确认 17 下传中 18 下传失败 20 供应商驳回 21 等待下传EDI，京东后台查询订单状态只有选全部可以查到，所以设为NULLABLE

	@Column(name = "CUSTOMIZED", nullable = false)
	private boolean customized; //定制状态 1 定制 0 非定制

	@Column(name = "CITY", nullable = false, length = 500)
	private String city; //分配机构，西安、上海等

	@Column(name = "REPOSITORY_ID")
	private Long repositoryId; //仓库表主键

	@Column(name = "REPOSITORY", length = 100)
	private String repository; //仓库

	@Column(name = "ADDRESS", nullable = false, length = 200)
	private String address; //仓库详细地址

	@Column(name = "CONTACTOR", nullable = false, length = 20)
	private String contactor; //联系人

	@Column(name = "CONTACT_NUMBER", length = 100)
	private String contactNumber; //联系方式

	@Column(name = "PURCHASER", nullable = false, length = 20)
	private String purchaser; //采购员

	@Column(name = "PURCHASE_TIME", nullable = false)
	private LocalDateTime purchaseTime; //采购时间

	@Column(name = "STORAGE_TIME")
	private LocalDateTime storageTime; //入库时间

	@Column(name = "BOOK_TIME")
	private LocalDateTime bookTime; //预约时间

	@Column(name = "ACCOUNT_PERIOD")
	private int accountPeriod = 30; //账期, 一般是30天

	@Column(name = "MARK")
	@Enumerated(EnumType.ORDINAL)
	private Mark mark; //贴码建议 1 需要贴码 0 不需要贴码 查询到的都是没有值的，所以设为NULLABLE

	@Column(name = "CONFIRM_STATUS", nullable = false)
	@Convert(converter = ConfirmStatusConverter.class)
	private ConfirmStatus confirmStatus = ConfirmStatus.NO; //回告状态: 0 未回告 1 回告成功 2 回告中 3 回告失败

	@Column(name = "REMARK")
	private String remark; //备注

	@Column(name = "CHEST_COUNT", nullable = false)
	private int chestCount; //波次数量

	@Column(name = "DELETED", nullable = false)
	private boolean deleted; //删除状态
	
	@Column(name="ARCHIVED", nullable=false)
	private boolean archived; //是否归档数据

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public boolean isCustomized() {
		return customized;
	}

	public void setCustomized(boolean customized) {
		this.customized = customized;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(Long repositoryId) {
		this.repositoryId = repositoryId;
	}

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

	public String getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}

	public LocalDateTime getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(LocalDateTime purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	public LocalDateTime getStorageTime() {
		return storageTime;
	}

	public void setStorageTime(LocalDateTime storageTime) {
		this.storageTime = storageTime;
	}

	public LocalDateTime getBookTime() {
		return bookTime;
	}

	public void setBookTime(LocalDateTime bookTime) {
		this.bookTime = bookTime;
	}

	public Mark getMark() {
		return mark;
	}

	public void setMark(Mark mark) {
		this.mark = mark;
	}

	public ConfirmStatus getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(ConfirmStatus confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getChestCount() {
		return chestCount;
	}

	public void setChestCount(int chestCount) {
		this.chestCount = chestCount;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public int getAccountPeriod() {
		return accountPeriod;
	}

	public void setAccountPeriod(int accountPeriod) {
		this.accountPeriod = accountPeriod;
	}

	/**
	 * JD Order可以唯一确定一笔订单
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof PurchaseOrder)) {
			return false;
		}
		PurchaseOrder purchaseOrder = (PurchaseOrder) o;
		return Objects.equals(orderId, purchaseOrder.getOrderId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId);
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}
	
}
