package com.cp.campers.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageHostReserveController {
	
	@GetMapping("/mypage_host_reserve")
	public String myPage() {
		return "mypage/mypage_host_reserve";
	}
}
