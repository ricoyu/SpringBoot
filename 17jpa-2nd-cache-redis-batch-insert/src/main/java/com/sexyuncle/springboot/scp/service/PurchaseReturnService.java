package com.sexyuncle.springboot.scp.service;

import static java.util.stream.Collectors.toList;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLongArray;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.loserico.cache.redis.JedisUtils;
import com.loserico.cache.redis.collection.ConcurrentMap;
import com.loserico.commons.utils.MathUtils;
import com.loserico.orm.dao.CriteriaOperations;
import com.loserico.orm.dao.EntityOperations;
import com.loserico.orm.dao.SQLOperations;
import com.sexyuncle.springboot.scp.entity.PurchaseReturn;
import com.sexyuncle.springboot.scp.entity.ReturnItem;
import com.sexyuncle.springboot.scp.enums.ReturnState;
import com.sexyuncle.springboot.scp.utils.ExcelUtils;
import com.sexyuncle.springboot.scp.utils.ReturnStateComparator;

/**
 * 退货单
 * <p>
 * Copyright: Copyright (c) 2018-05-21 14:06
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@Service
@Transactional
public class PurchaseReturnService {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseReturnService.class);

	@Autowired
	private EntityOperations entityOperations;

	@Autowired
	private CriteriaOperations criteriaOperations;

	@Autowired
	private SQLOperations sqlOperations;

	/**
	 * 返回新增/更新的记录数
	 * 
	 * @param data
	 * @return
	 */
	public AtomicLongArray uploadPurchaseReturns(MultipartFile file, ReturnState returnState) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(file.getBytes());
			Workbook workbook = ExcelUtils.getWorkbook(bais, file.getOriginalFilename());

			List<PurchaseReturn> purchaseReturns = new ArrayList<>(); //退货单
			List<ReturnItem> returnItems = new ArrayList<>(); //退货单项

			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.rowIterator();
			
			ConcurrentMap<Long, String> orderIdCityMap = null;
			//ConcurrentMap<Long, String> orderIdCityMap = JedisUtils.map("supplyChain:orderId:cities");
			while (iterator.hasNext()) {
				Row row = iterator.next();
				if (row.getRowNum() == 0) {
					continue;
				}
				purchaseReturns.add(toPurchaseReturn(row));
				returnItems.add(toReturnItem(row, orderIdCityMap));
			}

			//过滤重复的
			purchaseReturns = purchaseReturns.stream()
				.distinct()
				.collect(toList());
			/*
			 * 保存PurchaseReturn，如果根据returnId查到已经存在，则更新一下
			 */
			purchaseReturns.forEach((purchaseReturn) -> {
				purchaseReturn.setState(returnState); //上传的是什么状态的退货，这边就更新一下
			});
			
			List<Long> returnIds = purchaseReturns.stream().map(PurchaseReturn::getReturnId).collect(toList());
			List<PurchaseReturn> existingPurchaseReturns = criteriaOperations.findIn(PurchaseReturn.class, "returnId", returnIds);
			purchaseReturns.forEach((purchaseReturn) -> {
				for (PurchaseReturn existingPurchaseReturn : existingPurchaseReturns) {
					if (MathUtils.longEqual(existingPurchaseReturn.getReturnId(), purchaseReturn.getReturnId())) {
						purchaseReturn.setId(existingPurchaseReturn.getId());
						purchaseReturn.setCreateTime(existingPurchaseReturn.getCreateTime());
						purchaseReturn.setCreator(existingPurchaseReturn.getCreator());
						//已有退货单有状态，新上传的没有状态，则不更新已有退货单状态
						if(existingPurchaseReturn.getState() != null && purchaseReturn.getState() == null) {
							purchaseReturn.setState(existingPurchaseReturn.getState());
						} else if(existingPurchaseReturn.getState() != null && purchaseReturn.getState() != null) {
							//已有退货单和新上传退货单都有状态了，但是新退货单的状态是旧的，那么用已有退货单的状态
							if(ReturnStateComparator.compareTo(existingPurchaseReturn.getState(), purchaseReturn.getState()) > 0) {
								purchaseReturn.setState(existingPurchaseReturn.getState());
							}
						}
					}
				}
			});
			//根据退货单号排序
			purchaseReturns = purchaseReturns.stream()
					.sorted((prev, next) -> prev.getReturnId().compareTo(next.getReturnId()))
					.collect(toList());
			entityOperations.save(purchaseReturns);
			
			List<Long> skuReturnIds = returnItems.stream().map(ReturnItem::getReturnId).collect(toList());
			List<Long> skuCodes = returnItems.stream().map(ReturnItem::getSkuCode).collect(toList());
			Map<String, Object> params = new HashMap<>();
			params.put("returnIds", skuReturnIds);
			params.put("skuCodes", skuCodes);
			List<ReturnItem> existingReturnItems = sqlOperations.namedSqlQuery("findReturnItems", params, ReturnItem.class);

			//ReturnItem没有状态，已经录入的就不要再次录入了
			List<ReturnItem> noDuplicateReturnItems = returnItems.stream()
					.filter((returnItem) -> {
						boolean exists = false;
						for (ReturnItem existingReturnItem : existingReturnItems) {
							if (MathUtils.longEqual(existingReturnItem.getReturnId(), returnItem.getReturnId())
									&& MathUtils.longEqual(existingReturnItem.getSkuCode(), returnItem.getSkuCode())) {
								exists = true;
								break;
							}
						}
						return !exists;
					})
					.sorted((prev, next) -> {
						int code = prev.getReturnId().compareTo(next.getReturnId());
						if (code == 0) {
							return prev.getSkuCode().compareTo(next.getSkuCode());
						}
						return code;
					}).collect(toList());
			entityOperations.save(noDuplicateReturnItems);

		} catch (Exception e) {
			logger.error("msg", e);
		} finally {

		}
		return new AtomicLongArray(2);
	}

	/**
	 * 订单状态未设置，回告状态都是未回告，需要根据导入时前端选择的状态而定
	 * 
	 * @param row
	 * @return
	 */
	public PurchaseReturn toPurchaseReturn(Row row) {
		int returnIdIndex = 0;
		int supplierCodeIndex = 1;
		int supplierIndex = 2;
		
		PurchaseReturn purchaseReturn = new PurchaseReturn();
		purchaseReturn.setReturnId(ExcelUtils.longVal(row, returnIdIndex));
		purchaseReturn.setSupplier(ExcelUtils.stringVal(row, supplierIndex));
		purchaseReturn.setSupplierCode(ExcelUtils.stringVal(row, supplierCodeIndex));
		return purchaseReturn;
	}

	public ReturnItem toReturnItem(Row row, ConcurrentMap<Long, String> orderIdCityMap) {
		//ReturnItem, Sku
		int returnIdIndex = 0;
		int skuCodeIndex = 3;
		int skuNameIndex = 4;
		int qtyIndex = 5;
		int actualQtyIndex = 6;
		int unitPriceIndex = 7;
		int amountIndex = 8;
		int applyDateIndex = 9;
		int orderIdIndex = 10;
		int remarkIndex = 11;

		ReturnItem returnItem = new ReturnItem();
		returnItem.setSkuCode(ExcelUtils.longVal(row, skuCodeIndex));
		returnItem.setSkuName(ExcelUtils.stringVal(row, skuNameIndex));
		returnItem.setReturnId(ExcelUtils.longVal(row, returnIdIndex));
		returnItem.setOrderId(ExcelUtils.longVal(row, orderIdIndex));
		returnItem.setUnitPrice(ExcelUtils.bigDecimalVal(row, unitPriceIndex));
		returnItem.setQty(ExcelUtils.longVal(row, qtyIndex));
		returnItem.setActualQty(ExcelUtils.longVal(row, actualQtyIndex));
		returnItem.setAmount(ExcelUtils.bigDecimalVal(row, amountIndex));
		returnItem.setApplyDate(ExcelUtils.dateVal(row, applyDateIndex));
		returnItem.setRemark(ExcelUtils.stringVal(row, remarkIndex));
		returnItem.setCity(orderIdCityMap.get(returnItem.getOrderId()));
		return returnItem;
	}

}
