package com.cp.campers.search.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.campers.search.model.dao.SearchMapper;
import com.cp.campers.search.model.vo.PageInfo;
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
	public Map<String, Object> campAllSearch(int nowPage) {
		
		// 페이지 전체 개수 조회
		int listCount = searchMapper.campListCount();
		
		// 2. PageInfo 객체 만들기
		PageInfo pi = new PageInfo(nowPage, listCount, 10, 5);
		pi.setStartRow(nowPage, pi.getCampLimit());
		pi.setEndRow(pi.getStartRow(), pi.getCampLimit());
		
		// 3. 페이징 처리 된 게시글 목록 조회
		List<SearchCamp> campAllList = searchMapper.campAllSearch(pi);
		
		Map<String, Object> returnMap = new HashMap<>();
		
		if(listCount == 0) {
			returnMap.put("searchSize", "검색된 결과가 없습니다.");
		} else {
			returnMap.put("searchSize", listCount+1+"개의 캠핑장이 조회되었습니다.");
		}
		
		returnMap.put("pi", pi);
		returnMap.put("campAllList", campAllList);
		
		return returnMap;
	}

	@Override
	public int campListCount() {
		return searchMapper.campListCount();
	}

	//@Override
	//public int campFindCount(HashMap<String, Object> map) {
	//	return searchMapper.campFindCount(map);
	//}

	@Override
	public List<SearchCamp> campFindSearch(PageInfo pi, HashMap<String, Object> map) {
		return searchMapper.campFindSearch(pi, map);
	}

}
