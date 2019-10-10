package com.sexyuncle.springboot.scp.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sexyuncle.springboot.scp.enums.ConfirmStatus;
import com.sexyuncle.springboot.scp.enums.Mark;
import com.sexyuncle.springboot.scp.enums.OrderState;

public class PurchaseOrderSearchResultVO implements Serializable {
	private static final long serialVersionUID = 2592777518475038789L;

	private Long id;

	private Long orderId; //JD 订单号

	private String supplierCode; //供应商简码

	private String supplier;//供应商

	private String properties; //订单属性

	private OrderState state; //订单状态：0 新采购单 2 等待入库 3 已完成 4 部分收货 6 待审核 7 审核不通过 8 等待签收 15 待确认 17 下传中 18 下传失败 20 供应商驳回 21 等待下传EDI，京东后台查询订单状态只有选全部可以查到，所以设为NULLABLE

	private boolean customized; //定制状态 1 定制 0 非定制

	private String city; //分配机构，西安、上海等

	private Long repositoryId; //仓库表主键

	private String repository; //仓库

	private String address; //仓库详细地址

	private String contactor; //联系人

	private String contactNumber; //联系方式

	private String purchaser; //采购员

	private LocalDateTime purchaseTime; //采购时间

	private LocalDateTime storageTime; //入库时间

	private LocalDateTime bookTime; //预约时间

	private int accountPeriod = 30; //账期, 一般是30天

	private Mark mark; //贴码建议 1 需要贴码 0 不需要贴码 查询到的都是没有值的，所以设为NULLABLE

	private ConfirmStatus confirmStatus = ConfirmStatus.NO; //回告状态: 0 未回告 1 回告成功 2 回告中 3 回告失败

	private String remark; //备注

	private int chestCount; //波次数量

	private boolean deleted; //删除状态
	
	private boolean archived; //是否归档数据
	
	private BigDecimal actualAmount;

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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

	public int getAccountPeriod() {
		return accountPeriod;
	}

	public void setAccountPeriod(int accountPeriod) {
		this.accountPeriod = accountPeriod;
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

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}
	
}
