package com.cp.campers.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WishCampController {

	@GetMapping("/wishcamp")
	public String myPage() {
		return "mypage/wishcamp";
	}
}
