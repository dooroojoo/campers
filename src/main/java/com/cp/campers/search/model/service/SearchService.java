package com.cp.campers.search.model.service;

import java.util.List;
import java.util.Map;

import com.cp.campers.search.model.vo.FindCamp;


public interface SearchService {
	
	// 메인페이지에서 캠핑장 검색 조회
	Map<String, Object> mainSearch(FindCamp fc, int nowPage, List<String> typeArr);

	// 캠핑장 검색페이지에서 전체 조회
	Map<String, Object> campAllSearch(int nowPage);

	// 캠핑장 검색페이지에서 조건 검색 조회
	Map<String, Object> campFindSearch(FindCamp fc, List<String> typeArr, List<String> facilityArr,
			List<String> floorArr, int nowPage);

	
	

}