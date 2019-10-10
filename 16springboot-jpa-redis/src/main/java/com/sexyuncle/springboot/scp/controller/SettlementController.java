package com.sexyuncle.springboot.scp.controller;

import static com.loserico.commons.utils.StringUtils.rawJson;

import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicLongArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.loserico.orm.bean.Page;
import com.loserico.web.vo.Result;
import com.loserico.web.vo.Results;
import com.sexyuncle.springboot.scp.entity.PurchaseOrder;
import com.sexyuncle.springboot.scp.entity.Settlement;
import com.sexyuncle.springboot.scp.service.SettlementService;
import com.sexyuncle.springboot.scp.vo.PurchaseOrderSearchVO;
import com.sexyuncle.springboot.scp.vo.SettlementSearchVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("结算单")
@RestController
@CrossOrigin("*")
@RequestMapping("/settlement")
public class SettlementController {

	@Autowired
	private SettlementService settlementService;

	@ApiOperation(value = "上传结算单, 结算单文件名不需要固定格式", httpMethod = "POST",
			produces = "application/json;charset=UTF-8")
	@PostMapping("/upload")
	public Result uploadSettlement(MultipartFile file) {
		AtomicLongArray array = settlementService.uploadSettlement(file);
		return Results.builder()
				.success()
				.result(rawJson(array.toString()))
				.build();
	}
	
	@ApiOperation(value = "上传结算单明细, 结算单明细文件名不需要固定格式", httpMethod = "POST",
			produces = "application/json;charset=UTF-8")
	@PostMapping("/item/upload")
	public Result uploadSettlementItem(MultipartFile[] files) {
		for (int i = 0; i < files.length; i++) {
			MultipartFile multipartFile = files[i];
			settlementService.uploadSettlementItem(multipartFile);
		}
		return Results.builder()
				.success()
				.build();
	}
	

	@ApiOperation(value="查询结算单", httpMethod = "POST", produces = "application/json;charset=UTF-8")
	@PostMapping("/search")
	public Result searchSettlement(SettlementSearchVO settlementSearchVO, Page page) {
		List<Settlement> settlements = settlementService.searchSettlement(settlementSearchVO, page);
		return Results.builder()
				.success()
				.results(settlements)
				.page(page)
				.build();
	}
}
