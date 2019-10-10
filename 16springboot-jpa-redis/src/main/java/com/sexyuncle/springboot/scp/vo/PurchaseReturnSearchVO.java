package com.sexyuncle.springboot.scp.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.sexyuncle.springboot.scp.enums.ReturnState;

public class PurchaseReturnSearchVO implements Serializable {

	private static final long serialVersionUID = 3800575380332225082L;
	private Long returnId; //JD 京东退货单号
	private LocalDateTime applyDateBegin;
	private LocalDateTime applyDateEnd;
	private String city; //分配机构，西安、上海等
	private ReturnState state;//-1 所有, 1 初始退货单, 234 内部审核, 5 审核通过, 6 审核驳回, 9 等待预约出库, 10 等待目的机构入库, 11 等待退货区收货, 13 等待供应商收货, 17 已报废完成, 18 已提货完成, 20 厂直已完成
	private String purchaser;
	public Long getReturnId() {
		return returnId;
	}
	public void setReturnId(Long returnId) {
		this.returnId = returnId;
	}
	public LocalDateTime getApplyDateBegin() {
		return applyDateBegin;
	}
	public void setApplyDateBegin(LocalDateTime applyDateBegin) {
		this.applyDateBegin = applyDateBegin;
	}
	public LocalDateTime getApplyDateEnd() {
		return applyDateEnd;
	}
	public void setApplyDateEnd(LocalDateTime applyDateEnd) {
		this.applyDateEnd = applyDateEnd;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public ReturnState getState() {
		return state;
	}
	public void setState(ReturnState state) {
		this.state = state;
	}
	public String getPurchaser() {
		return purchaser;
	}
	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}
	
}
