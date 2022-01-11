package com.cp.campers.main.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cp.campers.main.model.service.MainService;
import com.cp.campers.main.model.vo.Recommend;


@Controller
public class MainController {

	private MainService mainService;
	
	@Autowired
	public MainController(MainService mainService) {
		this.mainService = mainService;
	}
	
	@GetMapping(value= {"/", "/main"})
	public ModelAndView main(ModelAndView mv){
		
		// 슬라이더 캠핑장 추천 리스트
		List<Recommend> mainSlider = mainService.mainSlider();
		// 메인페이지 예약순 3개 리스트 조회
		List<Recommend> mainBestList = mainService.mainBestList();
		// 메인페이지 신규순 3개 리스트 조회
		List<Recommend> mainNewList = mainService.mainNewList();
		
		
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
		
		mv.addObject("mainSlider", mainSlider);
		mv.addObject("mainBestList", mainBestList);
		mv.addObject("mainNewList", mainNewList);
		mv.setViewName("main/main");
		
		return mv;
	}
	
	
	@PostMapping(value="/")
	public String redirectMain() {
		return "redirect:/";
	}
	
	
	
}
