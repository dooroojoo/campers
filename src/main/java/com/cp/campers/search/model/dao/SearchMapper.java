package com.cp.campers.search.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.search.model.vo.SearchCamp;

@Mapper
public interface SearchMapper {
	
	/* 메인페이지에서 캠핑장 검색 조회 */
	List<SearchCamp> mainSearch(HashMap<String, Object> map);

}
