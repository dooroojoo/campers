package com.cp.campers.camp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String campDetail(int campNo, Model model) {
		
		Map<String, Object> map = campService.campDetail(campNo);
		model.addAttribute("camp", map.get("camp"));
		model.addAttribute("roomList", map.get("roomList"));
		model.addAttribute("newReply", '\n');
		
		return "camp/campDetail";
	}
	
}
