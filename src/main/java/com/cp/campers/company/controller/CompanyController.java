package com.cp.campers.company.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class CompanyController {

	@GetMapping("csCenter")
	public String csCenter() {
		return "company/csCenter";
	}
}
