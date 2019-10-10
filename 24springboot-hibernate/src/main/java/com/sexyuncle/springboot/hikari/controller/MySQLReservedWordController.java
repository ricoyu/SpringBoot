package com.sexyuncle.springboot.hikari.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loserico.web.vo.Result;
import com.loserico.web.vo.Results;
import com.sexyuncle.springboot.hikari.entity.ReservedWord;
import com.sexyuncle.springboot.hikari.service.MySQLReservedWordsService;
import com.sexyuncle.springboot.hikari.vo.ReservedWordSearchVO;

@RestController
public class MySQLReservedWordController {

	@Autowired
	private MySQLReservedWordsService reservedWordsService;

	@PostMapping("/reserved-words")
	public Result searchReservedWords(ReservedWordSearchVO reservedWordSearchVO) {
		List<ReservedWord> reservedWords = reservedWordsService.searchReservedWords(reservedWordSearchVO);
		return Results.success().results(reservedWords).build();
	}
}
