package com.sexyuncle.springboot.hikari.controller;

import com.loserico.common.lang.vo.Result;
import com.loserico.common.lang.vo.Results;
import com.sexyuncle.springboot.hikari.entity.ReservedWord;
import com.sexyuncle.springboot.hikari.service.MySQLReservedWordsService;
import com.sexyuncle.springboot.hikari.vo.ReservedWordSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MySQLReservedWordController {

	@Autowired
	private MySQLReservedWordsService reservedWordsService;

	@PostMapping("/reserved-words")
	public Result searchReservedWords(ReservedWordSearchVO reservedWordSearchVO) {
		List<ReservedWord> reservedWords = reservedWordsService.searchReservedWords(reservedWordSearchVO);
		return Results.success().results(reservedWords);
	}
}
