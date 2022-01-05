package com.cp.campers.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cp.campers.camp.model.service.CampService;

@Controller
@RequestMapping("/camp")
public class CampController {
	
	private CampService campService;
	
	@Autowired
	public CampController(CampService campService) {
		this.campService = campService;
	}

	@GetMapping("detail")
	public String campDetail(int campNo) {
		
		
		return "camp/campDetail";
	}
	
}
