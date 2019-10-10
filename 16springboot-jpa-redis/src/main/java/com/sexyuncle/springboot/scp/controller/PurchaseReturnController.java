package com.sexyuncle.springboot.scp.controller;

import static com.loserico.commons.utils.StringUtils.rawJson;
import static java.util.Optional.ofNullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicLongArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.loserico.commons.utils.EnumUtils;
import com.loserico.orm.bean.Page;
import com.loserico.web.vo.Result;
import com.loserico.web.vo.Results;
import com.sexyuncle.springboot.scp.enums.ReturnState;
import com.sexyuncle.springboot.scp.service.PurchaseReturnService;
import com.sexyuncle.springboot.scp.vo.PurchaseReturnSearchResultVO;
import com.sexyuncle.springboot.scp.vo.PurchaseReturnSearchVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("退货单")
@RestController
@CrossOrigin("*")
@RequestMapping("/purchase-return")
public class PurchaseReturnController {

	@Autowired
	private PurchaseReturnService purchaseReturnService;

	@ApiOperation(value = "上传退货单, 退货单文件名格式为：XXX-退货单状态.xls", httpMethod = "POST",
			produces = "application/json;charset=UTF-8")
	@PostMapping("/upload")
	public Result uploadPurchaseReturn(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		//20180101-20180510-已提货完成.xls
		String[] stateAndConfirmStatusArray = fileName.split("\\.")[0].split("-");
		int length = stateAndConfirmStatusArray.length;
		if (length < 1) {
			return Results.builder()
					.fail()
					.message("File name should be 20180101-20180510-ReturnState.xls(x)")
					.build();
		}
		ReturnState state = EnumUtils.toEnum(ReturnState.class, stateAndConfirmStatusArray[length - 1], "desc"); //退货单状态
		AtomicLongArray array = purchaseReturnService.uploadPurchaseReturns(file,
				ofNullable(state).orElse(ReturnState.INIT));
		return Results.builder()
				.success()
				.result(rawJson(array.toString()))
				.build();
	}
	

	@ApiOperation(value="查询退货单", httpMethod = "POST", produces = "application/json;charset=UTF-8")
	@PostMapping("/search")
	public Result searchPurchaseReturns(PurchaseReturnSearchVO purchaseReturnSearchVO, Page page) {
		List<PurchaseReturnSearchResultVO> purchaseReturns = purchaseReturnService.searchPurchaseReturn(purchaseReturnSearchVO, page);
		return Results.builder()
				.success()
				.results(purchaseReturns)
				.page(page)
				.build();
	}
}
