package com.cp.campers.search.model.service;

import java.util.HashMap;
import java.util.List;

import com.cp.campers.search.model.vo.SearchCamp;


public interface SearchService {
	
	/* 메인페이지에서 캠핑장 검색 조회 */
	List<SearchCamp> mainSearch(HashMap<String, Object> map);

	/* 캠핑장컴색페이지에서 전체 조회 */
	List<SearchCamp> campAllSearch();
	

}