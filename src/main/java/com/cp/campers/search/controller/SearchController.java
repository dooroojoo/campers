package com.cp.campers.search.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cp.campers.search.model.service.SearchService;
import com.cp.campers.search.model.vo.SearchCamp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/search")
public class SearchController {
	
	private SearchService searchService;
	
	@Autowired
	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}
	
	
	@GetMapping("camp")
	public String searchCamp() {
		return "search/searchCamp";
	}
	
	// 메인페이지 캠핑장 검색 기능
	@GetMapping("main")
	public String mainSearch(@RequestParam("area") String area,
							 @RequestParam("daterange") String date,
							 @RequestParam("guest") int guest,
							 @RequestParam List<String> type) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("area", area);
		map.put("date", date);
		map.put("guset", guest);
		
		for(String t : type) {
			map.put("type", t);
		}
		
		List<SearchCamp> mainSearch = searchService.mainSearch(map);
        
		
		return "search/searchCamp";
	}

}
