package com.sexyuncle.springboot.scp.controller;

import com.loserico.common.lang.vo.CommmonErrorType;
import com.loserico.common.lang.vo.Result;
import com.loserico.common.lang.vo.Results;
import com.sexyuncle.springboot.scp.service.DeliverCentreService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/deliver-centre")
public class DeliverCentreController {
	private static final Logger logger = LoggerFactory.getLogger(DeliverCentreController.class);

	@Autowired
	private DeliverCentreService deliverCentreService;

	@ApiOperation(value = "上传仓储中心列表", httpMethod = "POST", produces = "application/json;charset=UTF-8")
	@PostMapping("/upload")
	public Result upload(MultipartFile file) {
		int uploadCount;
		try {
			uploadCount = deliverCentreService.uploadDeliverCentre(new String(file.getBytes(), UTF_8));
			return Results.success()
					.result(uploadCount);
		} catch (IOException e) {
			logger.error("", e);
		}

		return Results.status(CommmonErrorType.INTERNAL_SERVER_ERROR).build();
	}
}
