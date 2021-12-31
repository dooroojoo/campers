package com.cp.campers.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageGuestReserveController {

	@GetMapping("/mypage_guest_reserve")
	public String myPage() {
		return "mypage/mypage_guest_reserve";
	}
}
