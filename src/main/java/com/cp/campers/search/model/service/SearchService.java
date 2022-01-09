package com.cp.campers.search.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cp.campers.search.model.vo.PageInfo;
import com.cp.campers.search.model.vo.SearchCamp;


public interface SearchService {
	
	// 메인페이지에서 캠핑장 검색 조회
	List<SearchCamp> mainSearch(HashMap<String, Object> map);

	// 캠핑장 검색페이지에서 전체 조회
	Map<String, Object> campAllSearch(int nowPage);

	// 게시글 총 개수 구하기
	int campListCount();
	
	// 조건 검색 총 개수 구하기
	// int campFindCount(HashMap<String, Object> map);

	// 캠핑장 검색페이지에서 조건 검색 조회
	List<SearchCamp> campFindSearch(PageInfo pi, HashMap<String, Object> map);

	
	

}