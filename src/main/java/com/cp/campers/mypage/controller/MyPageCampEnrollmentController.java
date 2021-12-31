package com.cp.campers.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageCampEnrollmentController {

	@GetMapping("/mypage_camp_enrollment")
	public String myPage() {
		return "mypage/mypage_camp_enrollment";
	}
}
