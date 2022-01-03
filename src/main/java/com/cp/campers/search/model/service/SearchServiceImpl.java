package com.cp.campers.search.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cp.campers.search.model.dao.SearchMapper;
import com.cp.campers.search.model.vo.SearchCamp;

@Service
public class SearchServiceImpl implements SearchService{
	
	private SearchMapper searchMapper;

	@Override
	public List<SearchCamp> mainSearch(HashMap<String, Object> map) {
		return searchMapper.mainSearch(map);
	}

}
