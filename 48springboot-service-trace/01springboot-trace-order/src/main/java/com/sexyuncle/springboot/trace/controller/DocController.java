package com.sexyuncle.springboot.trace.controller;

import com.sexyuncle.springboot.trace.vo.Document;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Copyright: (C), 2020/1/6 17:33
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RestController
@RequestMapping("/doc")
@Api("文档API")
public class DocController {
	
	private static List<Document> docs = Arrays.asList(
			new Document(1L, "怎么写出高质量代码"),
			new Document(2L, "怎么写出无法维护的代码")
	);
	
	@ApiOperation(value = "获取所有文档列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@GetMapping("/all")
	public List<Document> all() {
		return docs;
	}
	
	@ApiOperation(value = "查看文档详情", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@GetMapping("/detail")
	public String docDetail(Long id) {
		Optional<Document> opt = docs.stream().filter((doc) -> {
			return doc.getId().equals(id);
		}).findFirst();
		if (opt.isPresent()) {
			return opt.get().getTitle();
		}
		return null;
	}
}
