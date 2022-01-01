package com.cp.campers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class adminController {

	@GetMapping("/member")
	public String adminMember() {
		return "admin/memberList";
	}
}
