package com.sexyuncle.springboot.scp.service;

import static com.loserico.web.constants.StatusCodeConstants.ENTITY_NOT_FOUND;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import com.loserico.commons.utils.Arrays;
import com.loserico.commons.utils.EnumUtils;
import com.loserico.commons.utils.MathUtils;
import com.loserico.orm.bean.Page;
import com.loserico.orm.dao.CriteriaOperations;
import com.loserico.orm.dao.EntityOperations;
import com.loserico.orm.dao.SQLOperations;
import com.loserico.orm.utils.QueryUtils;
import com.loserico.web.exception.LocalizedException;
import com.sexyuncle.springboot.scp.entity.OrderItem;
import com.sexyuncle.springboot.scp.entity.PurchaseOrder;
import com.sexyuncle.springboot.scp.entity.Repository;
import com.sexyuncle.springboot.scp.entity.Sku;
import com.sexyuncle.springboot.scp.enums.ConfirmStatus;
import com.sexyuncle.springboot.scp.enums.Mark;
import com.sexyuncle.springboot.scp.enums.OrderState;
import com.sexyuncle.springboot.scp.utils.ExcelUtils;
import com.sexyuncle.springboot.scp.vo.PurchaseOrderSearchResultVO;
import com.sexyuncle.springboot.scp.vo.PurchaseOrderSearchVO;

@Service
@Transactional
public class PurchaseOrderService {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);

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
	public AtomicLongArray uploadPurchaseOrders(MultipartFile file, OrderState state, ConfirmStatus confirmStatus) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(file.getBytes());
			Workbook workbook = ExcelUtils.getWorkbook(bais, file.getOriginalFilename());

			List<PurchaseOrder> purchaseOrders = new ArrayList<>(); //订单
			List<OrderItem> orderItems = new ArrayList<>(); //订单项
			Set<Sku> skus = new HashSet<>(); //SKU

			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.rowIterator();
			while (iterator.hasNext()) {
				Row row = iterator.next();
				if (row.getRowNum() == 0) {
					continue;
				}
				purchaseOrders.add(toPurchaseOrder(row));
				orderItems.add(toOrderItem(row));
				skus.add(toSku(row));
			}

			/*
			 * 保存PurchaseOrder，如果根据orderId查到已经存在，则更新一下
			 */
			purchaseOrders.forEach((purchaseOrder) -> {
				purchaseOrder.setState(state); //上传的是什么状态的订单，这边就更新一下
				purchaseOrder.setConfirmStatus(confirmStatus); //上传订单是什么回告状态，这边也更新一下
			});
			List<Long> orderIds = purchaseOrders.stream().map(PurchaseOrder::getOrderId).collect(toList());
			List<PurchaseOrder> existingPurchaseOrders = criteriaOperations.findIn(PurchaseOrder.class, "orderId",
					orderIds);
			purchaseOrders.forEach((purchaseOrder) -> {
				for (PurchaseOrder existingPurchaseOrder : existingPurchaseOrders) {
					if (MathUtils.longEqual(existingPurchaseOrder.getOrderId(), purchaseOrder.getOrderId())) {
						purchaseOrder.setId(existingPurchaseOrder.getId());
						purchaseOrder.setCreateTime(existingPurchaseOrder.getCreateTime());
						purchaseOrder.setCreator(existingPurchaseOrder.getCreator());
						//已有订单有状态，新上传的没有状态，则不更新已有订单状态
						if (existingPurchaseOrder.getState() != null && purchaseOrder.getState() == null) {
							purchaseOrder.setState(existingPurchaseOrder.getState());
						} else if (existingPurchaseOrder.getState() != null && purchaseOrder.getState() != null) {
							//已有订单和新上传订单都有状态了，但是新订单的状态是旧的，那么用已有订单的状态
							if (existingPurchaseOrder.getState().compareTo(purchaseOrder.getState()) > 0) {
								purchaseOrder.setState(existingPurchaseOrder.getState());
							}
						}
						//已有订单有回告状态，新上传的没有回告状态，则不更新已有订单回告状态
						if (existingPurchaseOrder.getConfirmStatus() != null
								&& purchaseOrder.getConfirmStatus() == null) {
							purchaseOrder.setConfirmStatus(existingPurchaseOrder.getConfirmStatus());
						} else if (existingPurchaseOrder.getConfirmStatus() != null
								&& purchaseOrder.getConfirmStatus() != null) {
							//已有订单和新上传订单都有回告状态了，但是新订单的回告状态是旧的，那么用已有订单的回告状态
							if (existingPurchaseOrder.getConfirmStatus()
									.compareTo(purchaseOrder.getConfirmStatus()) > 0) {
								purchaseOrder.setConfirmStatus(existingPurchaseOrder.getConfirmStatus());
							}
						}
					}
				}
			});
			//根据订单号排序
			purchaseOrders = purchaseOrders.stream()
					.sorted((prev, next) -> prev.getOrderId().compareTo(next.getOrderId()))
					.collect(toList());
			entityOperations.save(purchaseOrders);

			List<Long> skuOrderIds = orderItems.stream().map(OrderItem::getOrderId).collect(toList());
			List<Long> skuCodes = orderItems.stream().map(OrderItem::getSkuCode).collect(toList());
			Map<String, Object> params = new HashMap<>();
			params.put("orderIds", skuOrderIds);
			params.put("skuCodes", skuCodes);
			List<OrderItem> existingOrderItems = sqlOperations.namedSqlQuery("findOrderItems", params, OrderItem.class);

			//OrderItem没有状态，已经录入的就不要再次录入了
			List<OrderItem> noDuplicateOrderItems = orderItems.stream()
					.filter((orderItem) -> {
						boolean exists = false;
						for (OrderItem existingOrderItem : existingOrderItems) {
							if (MathUtils.longEqual(existingOrderItem.getOrderId(), orderItem.getOrderId())
									&& MathUtils.longEqual(existingOrderItem.getSkuCode(), orderItem.getSkuCode())) {
								exists = true;
								break;
							}
						}
						return !exists;
					})
					.sorted((prev, next) -> {
						int code = prev.getOrderId().compareTo(next.getOrderId());
						if (code == 0) {
							return prev.getSkuCode().compareTo(next.getSkuCode());
						}
						return code;
					}).collect(toList());
			entityOperations.save(noDuplicateOrderItems);

			//重复的SKU也不需要录入
			List<Sku> existingSkus = criteriaOperations.findIn(Sku.class, "skuCode", skuCodes);
			List<Long> existingSkuCodes = existingSkus.stream().map(Sku::getSkuCode).collect(toList());
			List<Sku> noDuplicateSkus = skus.stream()
					.filter((sku) -> !existingSkuCodes.contains(sku.getSkuCode()))
					.sorted((prev, next) -> prev.getSkuCode().compareTo(next.getSkuCode()))
					.collect(toList());
			entityOperations.persist(noDuplicateSkus);

			//录入仓库
			List<String> existingRepositories = entityOperations.findAll(Repository.class).stream()
					.map(Repository::getRepository)
					.collect(toList());
			List<Repository> repositories = purchaseOrders.stream()
					.map((purchaseOrder) -> {
						Repository repository = new Repository();
						repository.setRepository(purchaseOrder.getRepository());
						repository.setAddress(purchaseOrder.getAddress());
						repository.setContactNumber(purchaseOrder.getContactNumber());
						repository.setContactor(purchaseOrder.getContactor());
						return repository;
					})
					.filter((repository) -> !existingRepositories.contains(repository.getRepository()))
					.sorted((prev, next) -> prev.getRepository().compareTo(next.getRepository()))
					.collect(toList());
			entityOperations.save(repositories);
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
	public PurchaseOrder toPurchaseOrder(Row row) {
		//PurchaseOrder
		int orderIdIndex = 0;
		int supplierCodeIndex = 1;
		int supplierIndex = 2;
		int propertiesIndex = 5;
		int cityIndex = 6;
		int repositoryIndex = 7;
		int addressIndex = 8;
		int contactor = 9;
		int contactNumberIndex = 10;
		int markIndex = 14;
		int purchaserIndex = 20;
		int purchasetimeIndex = 21;
		int storageTimeIndex = 22;
		int bookTimeIndex = 23;
		int remarkIndex = 24;
		int chestCountIndex = 25;

		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setOrderId(ExcelUtils.longVal(row, orderIdIndex));
		purchaseOrder.setSupplierCode(ExcelUtils.stringVal(row, supplierCodeIndex));
		purchaseOrder.setSupplier(ExcelUtils.stringVal(row, supplierIndex));
		purchaseOrder.setProperties(ExcelUtils.stringVal(row, propertiesIndex));
		purchaseOrder.setCity(ExcelUtils.stringVal(row, cityIndex));
		purchaseOrder.setRepository(ExcelUtils.stringVal(row, repositoryIndex));
		purchaseOrder.setAddress(ExcelUtils.stringVal(row, addressIndex));
		purchaseOrder.setContactor(ExcelUtils.stringVal(row, contactor));
		purchaseOrder.setContactNumber(ExcelUtils.stringVal(row, contactNumberIndex));
		purchaseOrder.setPurchaser(ExcelUtils.stringVal(row, purchaserIndex));
		purchaseOrder.setPurchaseTime(ExcelUtils.dateTimeVal(row, purchasetimeIndex));
		purchaseOrder.setStorageTime(ExcelUtils.dateTimeVal(row, storageTimeIndex));
		purchaseOrder.setBookTime(ExcelUtils.dateTimeVal(row, bookTimeIndex));
		purchaseOrder.setAccountPeriod(30); //导出的excel中没有这个字段，默认是30
		purchaseOrder.setMark(EnumUtils.toEnum(Mark.class, ExcelUtils.intVal(row, markIndex), "code"));
		purchaseOrder.setConfirmStatus(ConfirmStatus.NO);
		purchaseOrder.setRemark(ExcelUtils.stringVal(row, remarkIndex));
		purchaseOrder.setChestCount(ExcelUtils.intVal(row, chestCountIndex));
		purchaseOrder.setDeleted(false);

		return purchaseOrder;
	}

	public OrderItem toOrderItem(Row row) {
		//OrderItem, Sku
		int orderIdIndex = 0;
		int skuCodeIndex = 3;
		int skuNameIndex = 4;
		int unitPriceIndex = 11;
		int qtyIndex = 12;
		int actualQtyIndex = 13;
		int jdTagCountIndex = 15;
		int dupUpcIndex = 16;
		int upcIndex = 17;
		int amountIndex = 18;
		int actualAmountIndex = 19;

		OrderItem orderItem = new OrderItem();
		orderItem.setSkuCode(ExcelUtils.longVal(row, skuCodeIndex));
		orderItem.setSkuName(ExcelUtils.stringVal(row, skuNameIndex));
		orderItem.setOrderId(ExcelUtils.longVal(row, orderIdIndex));
		orderItem.setUnitPrice(ExcelUtils.bigDecimalVal(row, unitPriceIndex));
		orderItem.setQty(ExcelUtils.longVal(row, qtyIndex));
		orderItem.setActualQty(ExcelUtils.longVal(row, actualQtyIndex));
		orderItem.setJdTagCount(ExcelUtils.longVal(row, jdTagCountIndex));
		orderItem.setDupUpc(ExcelUtils.stringVal(row, dupUpcIndex));
		orderItem.setUpc(ExcelUtils.stringVal(row, upcIndex));
		orderItem.setAmount(ExcelUtils.bigDecimalVal(row, amountIndex));
		orderItem.setActualAmount(ExcelUtils.bigDecimalVal(row, actualAmountIndex));
		return orderItem;
	}

	public Sku toSku(Row row) {
		int skuCodeIndex = 3;
		int skuNameIndex = 4;
		Sku sku = new Sku();
		sku.setSkuCode(ExcelUtils.longVal(row, skuCodeIndex));
		sku.setSkuName(ExcelUtils.stringVal(row, skuNameIndex));
		return sku;
	}

	public void updateAccountPeriod(Long orderId, Integer accountPeriod) {
		PurchaseOrder purchaseOrder = criteriaOperations.findUniqueByProperty(PurchaseOrder.class, "orderId", orderId);
		if (purchaseOrder == null) {
			throw new LocalizedException(ENTITY_NOT_FOUND);
		}
		purchaseOrder.setAccountPeriod(accountPeriod);
	}

	public List<PurchaseOrderSearchResultVO> searchPurchaseOrder(PurchaseOrderSearchVO purchaseOrderSearchVO, Page page) {
		Map<String, Object> params = new HashMap<>();
		if (isNotBlank(purchaseOrderSearchVO.getCity())) {
			params.put("city", QueryUtils.innerMatch(purchaseOrderSearchVO.getCity()));
		}
		if (purchaseOrderSearchVO.getOrderIds() != null && purchaseOrderSearchVO.getOrderIds().length > 0) {
			params.put("orderIds", Arrays.nonNull(purchaseOrderSearchVO.getOrderIds()));
		}
		if (purchaseOrderSearchVO.getSkuCodes() != null && purchaseOrderSearchVO.getSkuCodes().length > 0) {
			params.put("skuCodes", Arrays.nonNull(purchaseOrderSearchVO.getSkuCodes()));
		}

		params.put("archived", purchaseOrderSearchVO.isArchived());
		params.put("deleted", purchaseOrderSearchVO.getDeleted());
		if (purchaseOrderSearchVO.getState() != null) {
			params.put("state", purchaseOrderSearchVO.getState().getCode());
		}
		if (purchaseOrderSearchVO.getConfirmStatus() != null) {
			params.put("confirmStatus", purchaseOrderSearchVO.getConfirmStatus().getCode());
		}
		params.put("customized", purchaseOrderSearchVO.getCustomized());
		if (purchaseOrderSearchVO.getMark() != null) {
			params.put("mark", purchaseOrderSearchVO.getMark());
		}
		params.put("purchaseTimeBegin", purchaseOrderSearchVO.getPurchaseTimeBegin());
		params.put("purchaseTimeEnd", purchaseOrderSearchVO.getPurchaseTimeEnd());
		params.put("purchaser", QueryUtils.innerMatch(purchaseOrderSearchVO.getPurchaser()));

		return sqlOperations.namedSqlQuery("queryPurchaserOrders", params, PurchaseOrderSearchResultVO.class, page);
	}
	
}
