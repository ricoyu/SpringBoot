package com.sexyuncle.springboot.scp.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.sexyuncle.springboot.scp.enums.ConfirmStatus;
import com.sexyuncle.springboot.scp.enums.Mark;
import com.sexyuncle.springboot.scp.enums.OrderState;

public class PurchaseOrderSearchVO implements Serializable {

	private static final long serialVersionUID = 412861853679094340L;

	private Long[] orderIds; //JD 订单号

	private Long[] skuCodes; //JD 商品编码
	
	private OrderState state; //订单状态：0 新采购单 2 等待入库 3 已完成 4 部分收货 6 待审核 7 审核不通过 8 等待签收 15 待确认 17 下传中 18 下传失败 20 供应商驳回 21 等待下传EDI，京东后台查询订单状态只有选全部可以查到，所以设为NULLABLE
	
	private Boolean customized; //定制状态 1 定制 0 非定制
	
	private ConfirmStatus confirmStatus; //回告状态: 0 未回告 1 回告成功 2 回告中 3 回告失败
	
	private Boolean deleted; //删除状态
	
	private String city; //分配机构，西安、上海等
	
	private Mark mark; //贴码建议 1 需要贴码 0 不需要贴码 查询到的都是没有值的，所以设为NULLABLE
	
	private LocalDateTime purchaseTimeBegin; //采购时间
	
	private LocalDateTime purchaseTimeEnd; //采购时间
	
	private String purchaser; //采购员
	
	private boolean archived; //是否归档数据

	public Long[] getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(Long[] orderIds) {
		this.orderIds = orderIds;
	}

	public Long[] getSkuCodes() {
		return skuCodes;
	}

	public void setSkuCodes(Long[] skuCodes) {
		this.skuCodes = skuCodes;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public ConfirmStatus getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(ConfirmStatus confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Mark getMark() {
		return mark;
	}

	public void setMark(Mark mark) {
		this.mark = mark;
	}

	public String getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public Boolean getCustomized() {
		return customized;
	}

	public void setCustomized(Boolean customized) {
		this.customized = customized;
	}

	public LocalDateTime getPurchaseTimeBegin() {
		return purchaseTimeBegin;
	}

	public void setPurchaseTimeBegin(LocalDateTime purchaseTimeBegin) {
		this.purchaseTimeBegin = purchaseTimeBegin;
	}

	public LocalDateTime getPurchaseTimeEnd() {
		return purchaseTimeEnd;
	}

	public void setPurchaseTimeEnd(LocalDateTime purchaseTimeEnd) {
		this.purchaseTimeEnd = purchaseTimeEnd;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
}
