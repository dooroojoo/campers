package com.cp.campers.search.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cp.campers.search.model.service.SearchService;
import com.cp.campers.search.model.vo.FindCamp;

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
		
		// 선택 없이 상세페이지 접속할 때 한달 후로 날짜 지정 (임시)
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar startCal = Calendar.getInstance( );
		Calendar endCal = Calendar.getInstance( );
		startCal.add( Calendar.MONTH, + 1 ); //다음달
		endCal.add( Calendar.MONTH, + 1 );
		endCal.add( Calendar.DATE, +1 ); //다음날
		
		String dateIn = (df.format(startCal.getTime()));  
		String dateOut = (df.format(endCal.getTime())); 
		

		mv.addObject("dateIn", dateIn);
		mv.addObject("dateOut", dateOut);
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
								@RequestParam(value="type", required=false, defaultValue="") List<String> typeArr,
								@RequestParam(value="facility", required=false, defaultValue="") List<String> facilityArr,
								@RequestParam(value="floor", required=false, defaultValue="") List<String> floorArr
										) throws ParseException {
		
		int nowPage = 1;
		
		if(page != null) {
			nowPage = Integer.parseInt(page);
		}
		
		log.info(area);
		log.info(daterange);
		log.info("guest : " + quantity);
		log.info("name : " + name);
		log.info("page : " + page);
		log.info("type : " + typeArr);
		log.info("faci : " + facilityArr);
		log.info("floor : " + floorArr);
		
		
		// 날짜 자르기
		String inDate = daterange.substring(0,10);
		String outDate = daterange.substring(13,23);

		
		FindCamp fc = new FindCamp();
		fc.setsName(name);
		fc.setsArea(area);
		fc.setsIn(inDate);
		fc.setsOut(outDate);
		fc.setsGuest(quantity);
		
		
		Map<String, Object> map = searchService.campFindSearch(fc, typeArr, facilityArr, floorArr, nowPage);
		
		
		// List로 넘어온 체크박스 String으로 합쳐주기
		String typeStr =String.join(",", typeArr);
		String faciStr =String.join(",", facilityArr);
		String floorStr =String.join(",", floorArr);
		
		
		
		mv.addObject("campFindSearch", map.get("campFindSearch"));
		mv.addObject("searchSize", map.get("searchSize"));
		mv.addObject("pi", map.get("pi"));
		mv.addObject("fc", fc);
		mv.addObject("type", typeArr);
		mv.addObject("facility", facilityArr);
		mv.addObject("floor", floorArr);
		mv.addObject("typeStr", typeStr);
		mv.addObject("faciStr", faciStr);
		mv.addObject("floorStr", floorStr);
		
		mv.setViewName("search/findCamp");
		
		return mv;
	}


	// 메인페이지 캠핑장 검색 기능
	@GetMapping("main")
	public ModelAndView mainSearch( ModelAndView mv,
									@RequestParam(value="page", required=false, defaultValue="1") String page,
									@RequestParam(value="area") String area,
									@RequestParam(value="daterange") String daterange,
									@RequestParam(value="guest") String quantity,
									@RequestParam(value="type", required=false, defaultValue="") List<String> typeArr
									) throws ParseException {
		
		
		
		int nowPage = 1;
		
		if(page != null) {
			nowPage = Integer.parseInt(page);
		}
		
		log.info(area);
		log.info(daterange);
		log.info("guest : " + quantity);
		log.info("page : " + page);
		log.info("type : " + typeArr);
		
		
		// 날짜 자르기
		String inDate = daterange.substring(0,10);
		String outDate = daterange.substring(13,23);
		
		FindCamp fc = new FindCamp();
		fc.setsArea(area);
		fc.setsIn(inDate);
		fc.setsOut(outDate);
		fc.setsGuest(quantity);
		

		Map<String, Object> map = searchService.mainSearch(fc, nowPage, typeArr);
		
		
		mv.addObject("campFindSearch", map.get("campFindSearch"));
		mv.addObject("searchSize", map.get("searchSize"));
		mv.addObject("pi", map.get("pi"));
		mv.addObject("fc", fc);
		mv.addObject("type", typeArr);
		mv.setViewName("search/findCamp");
		
		return mv;
	}
	

}
