package com.cp.campers.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SearchController {
	
	@GetMapping("/searchCamp")
	public String searchCamp() {
		return "search/searchCamp";
	}
	

}
