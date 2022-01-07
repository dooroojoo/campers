package com.cp.campers.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cp.campers.main.model.service.MainService;
import com.cp.campers.main.model.vo.Recommend;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		
		for(int i=0; i<mainSlider.size(); i++) {
			System.out.println(i);
		}
		
		mv.addObject("mainSlider", mainSlider);
		mv.setViewName("main/main");
		
		return mv;
	}
	
	
	@PostMapping(value="/")
	public String redirectMain() {
		return "redirect:/";
	}
	
	
	
}
