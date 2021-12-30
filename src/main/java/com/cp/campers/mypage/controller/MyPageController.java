package com.cp.campers.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {

	@GetMapping("/")
	public String myPage() {
		return "mypage/mypage";
	}
	
}
