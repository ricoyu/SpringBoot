package com.sexyuncle.springboot.scp.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sexyuncle.springboot.scp.enums.LifecycleStatus;
import com.sexyuncle.springboot.scp.enums.PayState;
import com.sexyuncle.springboot.scp.enums.SettlementAuditState;
import com.sexyuncle.springboot.scp.enums.SettlementState;
import com.sexyuncle.springboot.scp.jpa.converter.LifecycleStatusConverter;
import com.sexyuncle.springboot.scp.jpa.converter.PayStateConverter;
import com.sexyuncle.springboot.scp.jpa.converter.SettlementAuditStateConverter;
import com.sexyuncle.springboot.scp.jpa.converter.SettlementStateConverter;

@Entity
@Cacheable
@Cache(region = "settlement", usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "SETTLEMENT", schema = "SUPPLY_CHAIN_PORTAL")
public class Settlement extends BaseEntity {

	private static final long serialVersionUID = 607990034292652885L;

	@Id
	@GenericGenerator(name = "settlement-distributed-identifier",
			strategy = "com.peacefish.orm.commons.identifier.redis.RedisDistributedIdentifier",
			parameters = {
					@Parameter(name = "schema", value = "supply_chain_portal"),
					@Parameter(name = "table", value = "settlement"),
					@Parameter(name = "fetch_size", value = "10") })
	@GeneratedValue(generator = "settlement-distributed-identifier", strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "SETTLEMENT_ID", nullable = false)
	private Long settlementId; //JD 京东结算单号

	@Column(name = "OPERATOR", length = 50)
	private String operator; //结算员名称

	@Column(name = "APPLY_DATE", columnDefinition = "DATE", nullable = false)
	private LocalDate applyDate; //结算单申请日期

	@Column(name = "RECEIVABLE", nullable = false)
	private BigDecimal receivable; //JD应收合计

	@Column(name = "PAYABLE", nullable = false)
	private BigDecimal payable; //JD应付合计

	@Column(name = "AMOUNT", nullable = false)
	private BigDecimal amount; //JD应结合计

	@Column(name = "STATE", nullable = true)
	@Convert(converter = SettlementStateConverter.class)
	private SettlementState state; //确认状态, 2 供应商已确认, 4 系统驳回

	@Column(name = "LEFTCYCLE_STATUS", nullable = false)
	@Convert(converter = LifecycleStatusConverter.class)
	private LifecycleStatus lifecycleStatus = LifecycleStatus.SOURCE;

	@Column(name = "AUDIT_STATE")
	@Convert(converter = SettlementAuditStateConverter.class)
	private SettlementAuditState auditState; //结算单审核状态 101 待审核、 102 审核中、103 审核通过、104 审核驳回

	@Column(name = "PAY_STATE")
	@Convert(converter = PayStateConverter.class)
	private PayState payState; //付款状态(这个code是自己定的，其他状态都是取得JD) 1 未付款、2已付款、3 已开票

	//TODO 结算完成时间
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public BigDecimal getReceivable() {
		return receivable;
	}

	public void setReceivable(BigDecimal receivable) {
		this.receivable = receivable;
	}

	public BigDecimal getPayable() {
		return payable;
	}

	public void setPayable(BigDecimal payable) {
		this.payable = payable;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public SettlementState getState() {
		return state;
	}

	public void setState(SettlementState state) {
		this.state = state;
	}

	public SettlementAuditState getAuditState() {
		return auditState;
	}

	public void setAuditState(SettlementAuditState auditState) {
		this.auditState = auditState;
	}

	public PayState getPayState() {
		return payState;
	}

	public void setPayState(PayState payState) {
		this.payState = payState;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Settlement)) {
			return false;
		}
		Settlement settlement = (Settlement) o;
		return Objects.equals(settlementId, settlement.getSettlementId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(settlementId);
	}

	public LocalDate getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(LocalDate applyDate) {
		this.applyDate = applyDate;
	}

	public LifecycleStatus getLifecycleStatus() {
		return lifecycleStatus;
	}

	public void setLifecycleStatus(LifecycleStatus lifecycleStatus) {
		this.lifecycleStatus = lifecycleStatus;
	}
}
