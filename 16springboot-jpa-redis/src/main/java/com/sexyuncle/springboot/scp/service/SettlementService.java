package com.sexyuncle.springboot.scp.service;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongArray;

import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.loserico.commons.utils.Arrays;
import com.loserico.commons.utils.EnumUtils;
import com.loserico.commons.utils.MathUtils;
import com.loserico.orm.bean.Page;
import com.loserico.orm.dao.CriteriaOperations;
import com.loserico.orm.dao.EntityOperations;
import com.loserico.orm.dao.SQLOperations;
import com.loserico.web.exception.LocalizedException;
import com.sexyuncle.springboot.scp.entity.Settlement;
import com.sexyuncle.springboot.scp.entity.SettlementItem;
import com.sexyuncle.springboot.scp.enums.PayState;
import com.sexyuncle.springboot.scp.enums.SettlementAuditState;
import com.sexyuncle.springboot.scp.enums.SettlementState;
import com.sexyuncle.springboot.scp.enums.Tickets;
import com.sexyuncle.springboot.scp.utils.ExcelUtils;
import com.sexyuncle.springboot.scp.vo.SettlementSearchVO;

/**
 * 结算单
 * <p>
 * Copyright: Copyright (c) 2018-05-22 11:01
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@Service
@Transactional
public class SettlementService {

	private static final Logger logger = LoggerFactory.getLogger(SettlementService.class);

	@Autowired
	private EntityOperations entityOperations;

	@Autowired
	private CriteriaOperations criteriaOperations;

	@Autowired
	private SQLOperations sqlOperations;

	public AtomicLongArray uploadSettlement(MultipartFile file) {
		try {
			Workbook workbook = null;
			try {
				workbook = ExcelUtils.getWorkbook(file);
			} catch (NotOLE2FileException e) {
				throw new LocalizedException(1400, null, "您上传的文件格式不受支持");
			}

			List<Settlement> settlements = new ArrayList<>(); //结算单

			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.rowIterator();

			int rowNum = 0;
			while (iterator.hasNext()) {
				Row row = iterator.next();
				if (rowNum++ == 0) {
					continue;
				}
				settlements.add(toSettlement(row));
			}

			//过滤重复的
			settlements = settlements.stream()
					.distinct()
					.collect(toList());

			List<Long> settlementIds = settlements.stream().map(Settlement::getSettlementId).collect(toList());
			List<Settlement> existingSettlements = criteriaOperations.findIn(Settlement.class, "settlementId",
					settlementIds);
			settlements.forEach((settlement) -> {
				for (Settlement existingSettlement : existingSettlements) {
					if (MathUtils.longEqual(existingSettlement.getSettlementId(), settlement.getSettlementId())) {
						settlement.setId(existingSettlement.getId());
						settlement.setCreateTime(existingSettlement.getCreateTime());
						settlement.setCreator(existingSettlement.getCreator());
						/**
						 * 确认状态: 2 供应商已确认, 4 系统驳回								不同就更新,所以不需要比较
						 * 结算单审核状态: 101 待审核、 102 审核中、103 审核通过、104 审核驳回			状态更新的覆盖旧状态
						 * 付款状态(这个code是自己定的，其他状态都是取得JD) 1 未付款、2 已付款、3 已开票	状态更新的覆盖旧状态
						 * @on
						 */
						if (existingSettlement.getAuditState() != null && settlement.getAuditState() == null) {
							settlement.setAuditState(existingSettlement.getAuditState());
						} else if (existingSettlement.getAuditState() != null && settlement.getAuditState() != null) {
							//已有结算单和新上传结算单都有结算单审核状态了，但是新结算单的审核状态是旧的，那么用已有结算单的审核状态
							if (existingSettlement.getAuditState().compareTo(settlement.getAuditState()) > 0) {
								settlement.setState(existingSettlement.getState());
							}
						}
					}
				}
			});

			//根据结算单号排序
			settlements = settlements.stream()
					.sorted((prev, next) -> prev.getSettlementId().compareTo(next.getSettlementId()))
					.collect(toList());
			entityOperations.save(settlements);
		} catch (Exception e) {
			logger.error("msg", e);
		} finally {

		}
		return new AtomicLongArray(2);
	}

	public AtomicLongArray uploadSettlementItem(MultipartFile file) {
		try {
			Workbook workbook = ExcelUtils.getWorkbook(file);

			List<SettlementItem> settlementItems = new ArrayList<>(); //结算单明细

			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.rowIterator();

			int rowNum = 0;
			while (iterator.hasNext()) {
				Row row = iterator.next();
				if (rowNum++ == 0) { //CSV行号从1开始?
					continue;
				}
				settlementItems.add(toSettlementItem(row));
			}

			List<Long> settlementIds = settlementItems.stream()
					.map(SettlementItem::getSettlementId)
					.filter(Objects::nonNull)
					.distinct()
					.collect(toList());
			List<String> ticketIds = settlementItems.stream()
					.map(SettlementItem::getTicketId)
					.filter(Objects::nonNull)
					.distinct()
					.collect(toList());
			List<String> businessIds = settlementItems.stream()
					.map(SettlementItem::getBusinessId)
					.filter(Objects::nonNull)
					.distinct()
					.collect(toList());
			Map<String, Object> params = new HashMap<>();
			params.put("settlementIds", settlementIds);
			params.put("ticketIds", ticketIds);
			params.put("businessIds", businessIds);
			//导出的明细都是没有skuCode和skuName的，所以不作为匹配条件
			List<SettlementItem> existingSettlementItems = sqlOperations.namedSqlQuery("findSettlementItems", params,
					SettlementItem.class);

			//SettlementItem没有状态，已经录入的就不要再次录入了
			List<SettlementItem> noDuplicateSettlementItems = settlementItems.stream()
					.filter((settlementItem) -> !existingSettlementItems.contains(settlementItem))
					.sorted()
					.collect(toList());
			entityOperations.save(noDuplicateSettlementItems);

		} catch (Exception e) {
			logger.error("msg", e);
		} finally {

		}
		return new AtomicLongArray(2);
	}

	/**
	 * 结算单
	 * 
	 * @param row
	 * @return
	 */
	public Settlement toSettlement(Row row) {
		int settlementIdIndex = 0;
		int operatorIndex = 1;
		int applyDateIndex = 2;
		int receivableIndex = 3;
		int payableIndex = 4;
		int amountIndex = 5;
		int stateIndex = 6;
		int auditStateIndex = 7;
		int payStateIndex = 8;

		Settlement settlement = new Settlement();
		settlement.setSettlementId(ExcelUtils.longVal(row, settlementIdIndex));
		settlement.setOperator(ExcelUtils.stringVal(row, operatorIndex));
		settlement.setApplyDate(ExcelUtils.dateVal(row, applyDateIndex));
		settlement.setReceivable(ExcelUtils.bigDecimalVal(row, receivableIndex));
		settlement.setPayable(ExcelUtils.bigDecimalVal(row, payableIndex));
		settlement.setAmount(ExcelUtils.bigDecimalVal(row, amountIndex));
		String state = ExcelUtils.stringVal(row, stateIndex);
		settlement.setState(EnumUtils.toEnum(SettlementState.class, state, "desc"));
		String auditState = ExcelUtils.stringVal(row, auditStateIndex);
		settlement.setAuditState(EnumUtils.toEnum(SettlementAuditState.class, auditState, "desc"));
		String payState = ExcelUtils.stringVal(row, payStateIndex);
		settlement.setPayState(EnumUtils.toEnum(PayState.class, payState, "desc"));
		return settlement;
	}

	public SettlementItem toSettlementItem(Row row) {
		int settlementIdIndex = 0;
		int contractSubjectIndex = 1;
		int ticketIndex = 2;
		int ticketIdIndex = 3;
		int businessIdIndex = 4;
		int businessTimeIndex = 5;
		int deskTypeIndex = 6;
		int purchaserIndex = 7;
		int remarkIndex = 8;
		int skuCodeIndex = 9;
		int skuNameIndex = 10;
		int unitPriceIndex = 11;
		int qtyIndex = 12;
		int amountIndex = 13;

		SettlementItem settlementItem = new SettlementItem();
		settlementItem.setSettlementId(ExcelUtils.longVal(row, settlementIdIndex));
		settlementItem.setContractSubject(ExcelUtils.stringVal(row, contractSubjectIndex));
		settlementItem.setTicket(EnumUtils.toEnum(Tickets.class, ExcelUtils.stringVal(row, ticketIndex), "desc"));
		settlementItem.setTicketId(ExcelUtils.stringVal(row, ticketIdIndex));
		settlementItem.setBusinessId(ExcelUtils.stringVal(row, businessIdIndex));
		settlementItem.setBusinessTime(ExcelUtils.dateTimeVal(row, businessTimeIndex));
		settlementItem.setDeskType(ExcelUtils.stringVal(row, deskTypeIndex));
		settlementItem.setPurchaser(ExcelUtils.stringVal(row, purchaserIndex));
		settlementItem.setRemark(ExcelUtils.stringVal(row, remarkIndex));
		settlementItem.setSkuCode(ExcelUtils.longVal(row, skuCodeIndex));
		settlementItem.setSkuName(ExcelUtils.stringVal(row, skuNameIndex));
		settlementItem.setUnitPrice(ExcelUtils.bigDecimalVal(row, unitPriceIndex));
		settlementItem.setQty(ExcelUtils.longVal(row, qtyIndex));
		settlementItem.setAmount(ExcelUtils.bigDecimalVal(row, amountIndex));
		return settlementItem;
	}

	public List<Settlement> searchSettlement(SettlementSearchVO settlementSearchVO, Page page) {
		Map<String, Object> params = new HashMap<>();
		if (settlementSearchVO.getSettlementIds() != null && settlementSearchVO.getSettlementIds().length > 0) {
			params.put("settlementIds", Arrays.nonNull(settlementSearchVO.getSettlementIds()));
		}
		if (settlementSearchVO.getAuditState() != null) {
			params.put("auditState", Arrays.nonNull(settlementSearchVO.getAuditState()));
		}
		params.put("applyDateBegin", settlementSearchVO.getApplyDateBegin());
		params.put("applyDateEnd", settlementSearchVO.getApplyDateEnd());
		params.put("payState", settlementSearchVO.getPayState());
		params.put("state", settlementSearchVO.getState());

		return sqlOperations.namedSqlQuery("searchSettlement", params, Settlement.class, page);
	}

}
