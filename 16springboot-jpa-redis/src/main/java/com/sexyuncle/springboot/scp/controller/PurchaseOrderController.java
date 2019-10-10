package com.sexyuncle.springboot.scp.controller;

import static com.loserico.commons.utils.StringUtils.rawJson;
import static java.util.Optional.ofNullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicLongArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.loserico.commons.utils.EnumUtils;
import com.loserico.orm.bean.Page;
import com.loserico.web.vo.Result;
import com.loserico.web.vo.Results;
import com.sexyuncle.springboot.scp.entity.PurchaseOrder;
import com.sexyuncle.springboot.scp.enums.ConfirmStatus;
import com.sexyuncle.springboot.scp.enums.OrderState;
import com.sexyuncle.springboot.scp.service.PurchaseOrderService;
import com.sexyuncle.springboot.scp.vo.PurchaseOrderSearchResultVO;
import com.sexyuncle.springboot.scp.vo.PurchaseOrderSearchVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("订单/采购单")
@RestController
@CrossOrigin("*")
@RequestMapping("/purchase-order")
public class PurchaseOrderController {

	@Autowired
	private PurchaseOrderService purchaseOrderService;

	@ApiOperation(value = "上传订单，文件名格式为：xxx-订单状态-回告状态.xls(x), 返回新增/更新的记录数", httpMethod = "POST",
			produces = "application/json;charset=UTF-8")
	@PostMapping("/upload")
	public Result uploadPurchaseOrder(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String[] stateAndConfirmStatusArray = fileName.split("\\.")[0].split("-");
		int length = stateAndConfirmStatusArray.length;
		if (length < 2) {
			return Results.builder()
					.fail()
					.message("File name should be xxx-OrderStatus-ConfirmStatus.xls(x)")
					.build();
		}
		OrderState state = EnumUtils.toEnum(OrderState.class, stateAndConfirmStatusArray[length - 2], "desc"); //订单状态
		ConfirmStatus confirmStatus = EnumUtils.toEnum(ConfirmStatus.class, stateAndConfirmStatusArray[length - 1],
				"desc"); //回告状态
		AtomicLongArray array = purchaseOrderService.uploadPurchaseOrders(file,
				ofNullable(state).orElse(OrderState.NEW),
				ofNullable(confirmStatus).orElse(ConfirmStatus.NO));
		return Results.builder()
				.success()
				.result(rawJson(array.toString()))
				.build();
	}
	
	@ApiOperation(value="修改账期", httpMethod = "POST", produces = "application/json;charset=UTF-8")
	@PostMapping("/account-period/{orderId}/{accountPeriod}")
	public Result updateAccountPeriod(@PathVariable Long orderId, @PathVariable Integer accountPeriod) {
		 purchaseOrderService.updateAccountPeriod(orderId, accountPeriod);
		return Results.builder()
				.success()
				.build();
	}

	@ApiOperation(value="查询订单", httpMethod = "POST", produces = "application/json;charset=UTF-8")
	@PostMapping("/search")
	public Result searchPurchaseorder(PurchaseOrderSearchVO purchaseOrderSearchVO, Page page) {
		List<PurchaseOrderSearchResultVO> purchaseOrders = purchaseOrderService.searchPurchaseOrder(purchaseOrderSearchVO, page);
		return Results.builder()
				.success()
				.results(purchaseOrders)
				.page(page)
				.build();
	}
}
