package com.sexyuncle.springboot.scp.controller;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.loserico.web.vo.Result;
import com.loserico.web.vo.Results;
import com.sexyuncle.springboot.scp.service.DeliverCentreService;

import io.swagger.annotations.ApiOperation;

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
			return Results.builder()
					.result(uploadCount)
					.build();
		} catch (IOException e) {
			logger.error("", e);
		}

		return Results.builder()
				.fail()
				.build();
	}
}
