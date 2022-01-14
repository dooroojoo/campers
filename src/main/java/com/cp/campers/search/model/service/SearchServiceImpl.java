package com.cp.campers.search.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.campers.search.model.dao.SearchMapper;
import com.cp.campers.search.model.vo.FindCamp;
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
	public Map<String, Object> campAllSearch(int nowPage) {
		
		// 페이지 전체 개수 조회
		int listCount = searchMapper.campListCount();
		
		// 2. PageInfo 객체 만들기
		PageInfo pi = new PageInfo(nowPage, listCount, 10, 5);
		int startRow = (pi.getPage() - 1) * pi.getCampLimit() + 1;
		int endRow = startRow + pi.getCampLimit() - 1;
		
		// 3. 페이징 처리 된 게시글 목록 조회
		List<SearchCamp> campAllList = searchMapper.campAllSearch(startRow, endRow);
		
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
	public Map<String, Object> campFindSearch(FindCamp fc, List<String> typeArr, List<String> facilityArr,
			List<String> floorArr, int nowPage) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("fc", fc);
		map.put("type", typeArr);
		map.put("faci", facilityArr);
		map.put("floor", floorArr);
		
		
		// 검색된 캠핑장 개수 조회
		int listCount = searchMapper.campFindCount(map);
		System.out.println(listCount);

		// 2. PageInfo 객체 만들기
		PageInfo pi = new PageInfo(nowPage, listCount, 10, 5);
		int startRow = (pi.getPage() - 1) * pi.getCampLimit() + 1;
		int endRow = startRow + pi.getCampLimit() - 1;
		

		map.put("startRow", startRow);
		map.put("endRow", endRow);
		
		
		// 페이징 처리 된 게시글 목록 조회
		List<SearchCamp> campFindSearch = searchMapper.campFindSearch(map);

		Map<String, Object> returnMap = new HashMap<>();
		
		if(listCount == 0) {
			returnMap.put("searchSize", "검색된 캠핑장이 없습니다.");
		} else {
			returnMap.put("searchSize", listCount+"개의 캠핑장이 조회되었습니다.");
		}
		
		
		returnMap.put("campFindSearch", campFindSearch);
		returnMap.put("pi", pi);
		
		return returnMap;
	}
	
	
	@Override
	public Map<String, Object> mainSearch(FindCamp fc, int nowPage) {
		
		// 검색된 캠핑장 개수 조회
		int listCount = searchMapper.mainSearchCount(fc);
		System.out.println(listCount);

		// 2. PageInfo 객체 만들기
		PageInfo pi = new PageInfo(nowPage, listCount, 10, 5);
		int startRow = (pi.getPage() - 1) * pi.getCampLimit() + 1;
		int endRow = startRow + pi.getCampLimit() - 1;
				
		Map<String, Object> map = new HashMap<>();
		map.put("fc", fc);
		map.put("startRow", startRow);
		map.put("endRow", endRow);
				
		// 페이징 처리 된 게시글 목록 조회
		List<SearchCamp> campFindSearch = searchMapper.mainSearch(map);
		
		Map<String, Object> returnMap = new HashMap<>();
				
		if(listCount == 0) {
			returnMap.put("searchSize", "검색된 캠핑장이 없습니다.");
		} else {
			returnMap.put("searchSize", listCount+"개의 캠핑장이 조회되었습니다.");
		}
				
		returnMap.put("campFindSearch", campFindSearch);
		returnMap.put("pi", pi);
				
		return returnMap;
	}


}
