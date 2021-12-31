package com.cp.campers.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageCampManagementController {
	
	@GetMapping("/mypage_camp_management")
	public String myPage() {
		return "mypage/mypage_camp_management";
	}
}
