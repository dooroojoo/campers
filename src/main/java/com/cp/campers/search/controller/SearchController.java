package com.cp.campers.search.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cp.campers.search.model.service.SearchService;
import com.cp.campers.search.model.vo.PageInfo;
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
	public ModelAndView searchCamp(ModelAndView mv, PageInfo pi,
								@RequestParam(value="nowPage", required=false) String nowPage,
								@RequestParam(value="cntPerPage", required=false) String cntPerPage) {
		
		int total = searchService.campListCount();
		if(nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "10";
		}else if (nowPage == null) {
			nowPage = "1";
		}else if(cntPerPage == null) {
			cntPerPage = "10";
		}
		pi = new PageInfo(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage));
		
		
		List<SearchCamp> campAllSearch = searchService.campAllSearch(pi);
		
		
		if(campAllSearch.size() == 0) {
			mv.addObject("searchSize", "검색된 결과가 없습니다.");
		} else {
			mv.addObject("searchSize", campAllSearch.size()+"개의 결과가 조회되었습니다.");
		}
		
		mv.addObject("paging", pi);
		mv.addObject("campSearch", campAllSearch);
		mv.setViewName("search/searchCamp");
		
		return mv;
	}

	
	// 메인페이지 캠핑장 검색 기능
	@GetMapping("main")
	public ModelAndView mainSearch(@RequestParam("area") String area,
							 @RequestParam("daterange") String date,
							 @RequestParam("guest") String guest,
							 @RequestParam(value="camping", required =false, defaultValue="null") String camping,
							 @RequestParam(value="glamping", required =false, defaultValue="null") String glamping,
							 @RequestParam(value="caravan", required =false, defaultValue="null") String caravan,
							 ModelAndView mv) throws ParseException {
		
		log.info(area);
		log.info(date);
		log.info(guest);
		log.info(camping);
		log.info(glamping);
		log.info(caravan);
		
		
		// String 날짜로 변환
		String inDate = date.substring(0,9);
		String outDate = date.substring(13,22);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date checkIn = formatter.parse(inDate);
		Date checkOut = formatter.parse(outDate);

		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("area", area);
		map.put("checkIn", checkIn);
		map.put("checkOut", checkOut);
		map.put("guest", guest);
		map.put("camping", camping);
		map.put("glamping", glamping);
		map.put("caravan", caravan);
		
		List<SearchCamp> mainSearch = searchService.mainSearch(map);
		
		if(mainSearch.size() == 0) {
			mv.addObject("searchSize", "검색된 결과가 없습니다.");
		} else {
			mv.addObject("searchSize", mainSearch.size()+"개의 결과가 조회되었습니다.");
		}
		
		mv.addObject("campSearch", mainSearch);
		mv.setViewName("search/searchCamp");
		
		return mv;
	}

}
