package com.sexyuncle.springboot.scp.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sexyuncle.springboot.scp.enums.PayState;
import com.sexyuncle.springboot.scp.enums.SettlementAuditState;
import com.sexyuncle.springboot.scp.enums.SettlementState;

public class SettlementSearchVO implements Serializable {

	private static final long serialVersionUID = 412861853679094340L;

	private Long[] settlementIds; //JD 京东结算单号

	private SettlementAuditState auditState; //结算单审核状态 101 待审核、 102 审核中、103 审核通过、104 审核驳回

	private LocalDate applyDateBegin;
	
	private LocalDate applyDateEnd;

	private PayState payState; //付款状态(这个code是自己定的，其他状态都是取得JD) 1 未付款、2已付款、3 已开票

	private LocalDateTime purchaseTimeEnd; //采购时间

	private SettlementState state; //确认状态, 2 供应商已确认, 4 系统驳回

	public Long[] getSettlementIds() {
		return settlementIds;
	}

	public void setSettlementIds(Long[] settlementIds) {
		this.settlementIds = settlementIds;
	}

	public SettlementAuditState getAuditState() {
		return auditState;
	}

	public void setAuditState(SettlementAuditState auditState) {
		this.auditState = auditState;
	}

	public LocalDate getApplyDateBegin() {
		return applyDateBegin;
	}

	public void setApplyDateBegin(LocalDate applyDateBegin) {
		this.applyDateBegin = applyDateBegin;
	}

	public LocalDate getApplyDateEnd() {
		return applyDateEnd;
	}

	public void setApplyDateEnd(LocalDate applyDateEnd) {
		this.applyDateEnd = applyDateEnd;
	}

	public PayState getPayState() {
		return payState;
	}

	public void setPayState(PayState payState) {
		this.payState = payState;
	}

	public LocalDateTime getPurchaseTimeEnd() {
		return purchaseTimeEnd;
	}

	public void setPurchaseTimeEnd(LocalDateTime purchaseTimeEnd) {
		this.purchaseTimeEnd = purchaseTimeEnd;
	}

	public SettlementState getState() {
		return state;
	}

	public void setState(SettlementState state) {
		this.state = state;
	}

}
