package com.sexyuncle.springboot.scp.entity;

import static org.apache.commons.lang3.ObjectUtils.compare;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

import com.sexyuncle.springboot.scp.enums.Tickets;
import com.sexyuncle.springboot.scp.jpa.converter.TicketsConverter;

@Entity
@Cacheable
@Cache(region = "settlementItem", usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "SETTLEMENT_ITEM", schema = "SUPPLY_CHAIN_PORTAL")
public class SettlementItem extends BaseEntity implements Comparable<SettlementItem> {

	private static final long serialVersionUID = -5998492224689377462L;

	@Id
	@GenericGenerator(name = "settlement_item-distributed-identifier",
			strategy = "com.peacefish.orm.commons.identifier.redis.RedisDistributedIdentifier",
			parameters = {
					@Parameter(name = "schema", value = "supply_chain_portal"),
					@Parameter(name = "table", value = "SETTLEMENT_ITEM"),
					@Parameter(name = "fetch_size", value = "10") })
	@GeneratedValue(generator = "settlement_item-distributed-identifier", strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "SETTLEMENT_ID", nullable = false)
	private Long settlementId; //JD 京东结算单号

	@Column(name = "CONTRACT_SUBJECT", nullable = false, length = 200)
	private String contractSubject;

	@Column(name = "TICKET", nullable = false)
	@Convert(converter = TicketsConverter.class)
	private Tickets ticket; //应付帐单据类型

	@Column(name = "TICKET_ID", nullable = false)
	private String ticketId; //JD 订单号

	@Column(name = "BUSINESS_ID", nullable = false, length = 20)
	private String businessId; //JD 业务单号

	@Column(name = "BUSINESS_TIME", columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime businessTime; //业务发生时间

	@Column(name = "DESK_TYPE", length = 50)
	private String deskType; //台账类型

	@Column(name = "PURCHASER", nullable = false, length = 20)
	private String purchaser; //采购员

	@Column(name = "REMARK")
	private String remark; //备注

	@Column(name = "SKU_CODE")
	private Long skuCode; //JD 商品编码

	@Column(name = "SKU_NAME")
	private String skuName; //商品名称

	@Column(name = "UNIT_PRICE", nullable = false)
	private BigDecimal unitPrice; //单价

	@Column(name = "QTY")
	private Long qty; //数量,导出的结算单明细里面数量是没有值的，所以置为nullable

	@Column(name = "AMOUNT")
	private BigDecimal amount; //金额

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

	public Tickets getTicket() {
		return ticket;
	}

	public void setTicket(Tickets ticket) {
		this.ticket = ticket;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public LocalDateTime getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(LocalDateTime businessTime) {
		this.businessTime = businessTime;
	}

	public String getDeskType() {
		return deskType;
	}

	public void setDeskType(String deskType) {
		this.deskType = deskType;
	}

	public String getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getContractSubject() {
		return contractSubject;
	}

	public void setContractSubject(String contractSubject) {
		this.contractSubject = contractSubject;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof SettlementItem)) {
			return false;
		}
		SettlementItem settlementItem = (SettlementItem) o;
		return Objects.equals(settlementId, settlementItem.getSettlementId()) &&
				Objects.equals(ticket, settlementItem.getTicket()) &&
				Objects.equals(ticketId, settlementItem.getTicketId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(settlementId, ticket, ticketId);
	}

	@Override
	public int compareTo(SettlementItem next) {
		int code1 = compare(settlementId, next.getSettlementId());
		if (code1 == 0) {
			int code2 = compare(ticket, next.getTicket());
			if (code2 == 0) {
				int code3 = compare(ticketId, next.getTicketId());
				if (code3 == 0) {
					return compare(businessId, next.getBusinessId());
				}
				return code3;
			} else {
				return code2;
			}

		}
		return code1;
	}
}
