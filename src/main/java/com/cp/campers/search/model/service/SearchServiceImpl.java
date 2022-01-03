package com.cp.campers.search.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.campers.search.model.dao.SearchMapper;
import com.cp.campers.search.model.vo.SearchCamp;

@Service("searchService")
public class SearchServiceImpl implements SearchService{
	
	private SearchMapper searchMapper;
	
	@Autowired
	public SearchServiceImpl(SearchMapper searchMapper) {
		this.searchMapper = searchMapper;
	}

	@Override
	public List<SearchCamp> mainSearch(HashMap<String, Object> map) {
		return searchMapper.mainSearch(map);
	}

	@Override
	public List<SearchCamp> campAllSearch() {
		return searchMapper.campAllSearch();
	}

}
