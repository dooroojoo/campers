package com.cp.campers.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
	public String main(Model model) {
		
		// 슬라이더 캠핑장 추천 리스트
		List<Recommend> mainSlider = mainService.mainSlider();
		
		model.addAttribute("mainSlider", mainSlider);
		return "main/main";
	}
	
	@PostMapping(value="/")
	public String redirectMain() {
		return "redirect:/";
	}
	
	
	
}
