package com.cp.campers.search.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cp.campers.search.model.vo.FindCamp;
import com.cp.campers.search.model.vo.SearchCamp;


public interface SearchService {
	
	// 메인페이지에서 캠핑장 검색 조회
	List<SearchCamp> mainSearch(HashMap<String, Object> map);

	// 캠핑장 검색페이지에서 전체 조회
	Map<String, Object> campAllSearch(int nowPage);

	// 캠핑장 검색페이지에서 조건 검색 조회
	Map<String, Object> campFindSearch(FindCamp fc, int nowPage);

	

	
	

}