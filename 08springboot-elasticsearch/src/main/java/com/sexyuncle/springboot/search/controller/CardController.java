package com.sexyuncle.springboot.search.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sexyuncle.springboot.search.entity.BaseballCard;
import com.sexyuncle.springboot.search.service.HibernateSearchService;

@Controller
public class CardController {
	
	private static final Logger logger = LoggerFactory.getLogger(CardController.class);

	@Autowired
	private HibernateSearchService searchservice;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String search(@RequestParam(value = "search", required = false) String q, Model model) {
		List<BaseballCard> searchResults = null;
		try {
			searchResults = searchservice.fuzzySearch(q);

		} catch (Exception ex) {
			logger.error("msg", ex);
		}
		model.addAttribute("search", searchResults);
		return "index";

	}

}