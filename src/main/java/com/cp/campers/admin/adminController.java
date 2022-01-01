package com.cp.campers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class adminController {

	@GetMapping("/member")
	public String adminMember() {
		return "admin/adminMember";
	}
	
	@GetMapping("/report")
	public String adminReport() {
		return "admin/adminReport";
	}
	
	@GetMapping("/camp")
	public String adminCamp() {
		return "admin/adminCamp";
	}
	
	@GetMapping("/camp/detail")
	public String adminCampDetail() {
		return "admin/campDetail";
	}
}
