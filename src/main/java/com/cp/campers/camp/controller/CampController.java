package com.cp.campers.camp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/camp")
public class CampController {

	@GetMapping("detail")
	public String campDetail() {
		return "camp/campDetail";
	}
	
}
