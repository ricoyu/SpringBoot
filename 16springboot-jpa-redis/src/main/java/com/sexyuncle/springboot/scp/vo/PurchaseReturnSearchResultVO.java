package com.sexyuncle.springboot.scp.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sexyuncle.springboot.scp.enums.ReturnState;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class PurchaseReturnSearchResultVO implements Serializable {
	
	private static final long serialVersionUID = 5816049146073385352L;

	private Long id;
	
	@ApiModelProperty(value="JD 京东退货单号", required=true)
	private Long returnId; //JD 京东退货单号

	@ApiModelProperty(value="目的机构", required=true)
	private String city;
	
	@ApiModelProperty(value="制单日期", required=true)
	private LocalDateTime applyDate;
	
	@ApiModelProperty(value="出库时间", required=true)
	private LocalDateTime deliveryDate;
	
	@ApiModelProperty(value="供应商名称", required=true)
	private String supplier;//供应商
	
	@ApiModelProperty(value="金额", required=true)
	private BigDecimal amount;
	
	@ApiModelProperty(value="订单状态", required=true)
	private ReturnState state;
	
	@ApiModelProperty(value="退货类型,写死了普通", required=true)
	private String returnType;
	
	@ApiModelProperty(value="TC转运,否", required=true)
	private String tcTransfer;

	public Long getReturnId() {
		return returnId;
	}

	public void setReturnId(Long returnId) {
		this.returnId = returnId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public LocalDateTime getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(LocalDateTime applyDate) {
		this.applyDate = applyDate;
	}

	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public ReturnState getState() {
		return state;
	}

	public void setState(ReturnState state) {
		this.state = state;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getTcTransfer() {
		return tcTransfer;
	}

	public void setTcTransfer(String tcTransfer) {
		this.tcTransfer = tcTransfer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
