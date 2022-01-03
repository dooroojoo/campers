package com.cp.campers.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/search")
public class SearchController {
	
	@GetMapping("camp")
	public String searchCamp() {
		return "search/searchCamp";
	}
	
	@GetMapping("main")
	public String mainSearch(@RequestParam("area") String area,
							 @RequestParam("daterange") String date,
							 @RequestParam("guest") int guest
							) {
		
		return "search/searchCamp";
	}

}
