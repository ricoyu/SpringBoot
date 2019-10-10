package com.sexyuncle.springboot.hikari.service;

import static com.loserico.commons.jackson.JacksonUtils.toJson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loserico.orm.dao.SQLOperations;
import com.loserico.orm.utils.QueryUtils;
import com.sexyuncle.springboot.hikari.entity.ReservedWord;
import com.sexyuncle.springboot.hikari.vo.ReservedWordSearchVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MySQLReservedWordsService {

	@Autowired
	private SQLOperations sqlOperations;

	public List<ReservedWord> searchReservedWords(ReservedWordSearchVO reservedWordSearchVO) {
		Map<String, Object> params = new HashMap<>();
		QueryUtils.innerMatch(params, "reservedWord", reservedWordSearchVO.getReservedWord());
		QueryUtils.innerMatch(params, "reservedType", reservedWordSearchVO.getReservedType());

		log.info("查询参数: {}", toJson(params));
		return sqlOperations.namedSqlQuery("searchReservedWords", params, ReservedWord.class);
	}
}
