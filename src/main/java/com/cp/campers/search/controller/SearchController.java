package com.cp.campers.search.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cp.campers.search.model.service.SearchService;
import com.cp.campers.search.model.vo.FindCamp;
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
	
	// 캠핑장검색 페이지 이동 시 전체 목록 조회
	@GetMapping("camp")
	public ModelAndView searchCamp(ModelAndView mv,
								@RequestParam(value="page", required=false) String page) {
		
		int nowPage = 1;
		
		if(page != null) {
			nowPage = Integer.parseInt(page);
		}
		
		Map<String, Object> map = searchService.campAllSearch(nowPage);
		
		mv.addObject("pi", map.get("pi"));
		mv.addObject("campSearch", map.get("campAllList"));
		mv.addObject("searchSize", map.get("searchSize"));
		mv.setViewName("search/searchCamp");
		
		return mv;
	}
	
	
	// 캠핑장검색 페이지에서 조건 검색 조회
	@GetMapping("find")
	public ModelAndView findCamp(ModelAndView mv,
								@RequestParam(value="page", required=false, defaultValue="1") String page,
								@RequestParam(value="area") String area,
								@RequestParam(value="daterange") String daterange,
								@RequestParam(value="quantity") String quantity,
								@RequestParam(value="name", required=false, defaultValue="") String name,
								@RequestParam(value="type", required=false, defaultValue="null") List<String> typeArr,
								@RequestParam(value="facility", required=false, defaultValue="null") List<String> facilityArr,
								@RequestParam(value="floor", required=false, defaultValue="null") List<String> floorArr
										) throws ParseException {
		
		int nowPage = 1;
		
		if(page != null) {
			nowPage = Integer.parseInt(page);
		}
		
		
		String type =String.join(", ", typeArr);
		String facility =String.join(", ", facilityArr);
		String floor =String.join(", ", floorArr);
		
		log.info(area);
		log.info(daterange);
		log.info(quantity);
		log.info(name);
		log.info(page);
		log.info(type);
		log.info(facility);
		log.info(floor);
		
		
		// String -> Date로 변환
		String inDate = daterange.substring(0,10);
		String outDate = daterange.substring(13,23);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
		LocalDate checkIn = LocalDate.parse(inDate, formatter);
		LocalDate checkOut = LocalDate.parse(outDate, formatter);

		
		FindCamp fc = new FindCamp();
		fc.setsName(name);
		fc.setsArea(area);
		fc.setsIn(checkIn);
		fc.setsOut(checkOut);
		fc.setsGuest(quantity);
		fc.setsFaci(facility);
		fc.setsFloor(floor);
		fc.setsType(type);
		
		
		Map<String, Object> map = searchService.campFindSearch(fc, nowPage);
		
		
		mv.addObject("campFindSearch", map.get("campFindSearch"));
		mv.addObject("searchSize", map.get("searchSize"));
		mv.addObject("pi", map.get("pi"));
		mv.addObject("fc", fc);
		mv.setViewName("search/findCamp");
		
		return mv;
	}


	// 메인페이지 캠핑장 검색 기능
	@GetMapping("main")
	public ModelAndView mainSearch( PageInfo pi, ModelAndView mv,
							 @RequestParam(value="nowPage", required=false) String nowPage,
							 @RequestParam(value="cntPerPage", required=false) String cntPerPage,
							 @RequestParam("area") String area,
							 @RequestParam("daterange") String date,
							 @RequestParam("guest") String guest,
							 @RequestParam(value="typeArr", required =false, defaultValue="") List<String> typeArr
							 ) throws ParseException {
		
		
		
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
		
		List<SearchCamp> mainSearch = searchService.mainSearch(map);
		
		if(mainSearch.size() == 0) {
			mv.addObject("searchSize", "검색된 결과가 없습니다.");
		} else {
			mv.addObject("searchSize", mainSearch.size()+"개의 결과가 조회되었습니다.");
		}
		
		mv.addObject("paging", pi);
		mv.addObject("campSearch", mainSearch);
		mv.setViewName("search/searchCamp");
		
		return mv;
	}

}
