package com.cp.campers.search.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@ResponseBody
	public ModelAndView findCamp(ModelAndView mv, FindCamp fc,
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
		log.info(quantity);
		log.info(name);
		log.info(page);
		
		// String -> Date로 변환
		String inDate = daterange.substring(0,9);
		String outDate = daterange.substring(13,22);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date checkIn = formatter.parse(inDate);
		Date checkOut = formatter.parse(outDate);
		
		fc.setsName(name);
		fc.setsArea(area);
		fc.setsIn(checkIn);
		fc.setsOut(checkOut);
		fc.setsGuest(quantity);
		fc.setsFaci(facilityArr);
		fc.setsFloor(floorArr);
		fc.setsType(typeArr);
		
		Map<String, Object> map = searchService.campFindSearch(fc, nowPage);
		
		mv.addObject("campFindSearch", map.get("campFindSearch"));
		mv.addObject("searchSize", map.get("searchSize"));
		mv.addObject("pi", map.get("pi"));
		//mv.addObject("fc", fc);
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
		
		
		// 아직 페이징 처리 적용안함
		// int total = searchService.campListCount();
		if(nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "10";
		}else if (nowPage == null) {
			nowPage = "1";
		}else if(cntPerPage == null) {
			cntPerPage = "10";
		}
		
		
		
		// String 날짜로 변환
		String inDate = date.substring(0,9);
		String outDate = date.substring(13,22);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date checkIn = formatter.parse(inDate);
		Date checkOut = formatter.parse(outDate);
		
		String type ="";
		for(int i=0; i<typeArr.size(); i++) {
			if(typeArr.size() > 1 && i < typeArr.size()-1 ) {
				type += typeArr.get(i) + "|";
			} else {
				type += typeArr.get(i);
			}
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("area", area);
		map.put("checkIn", checkIn);
		map.put("checkOut", checkOut);
		map.put("guest", guest);
		map.put("type", type);
		
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
