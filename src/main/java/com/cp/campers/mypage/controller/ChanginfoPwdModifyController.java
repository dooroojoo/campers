package com.cp.campers.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChanginfoPwdModifyController {

	@GetMapping("/changinfo_pwd_modify")
	public String myPage() {
		return "mypage/changinfo_pwd_modify";
	}
}