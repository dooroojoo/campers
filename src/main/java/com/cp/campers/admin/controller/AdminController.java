package com.cp.campers.admin.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cp.campers.admin.model.service.AdminService;
import com.cp.campers.member.model.vo.Member;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private AdminService adminService;
	private MessageSource messageSource;
	
	@Autowired
	public AdminController(AdminService adminService, MessageSource messageSource) {
		this.adminService = adminService;
		this.messageSource = messageSource;
	}

	@GetMapping("member")
	public ModelAndView adminMember(ModelAndView mv) {
		
		List<Member> memberList = adminService.findAllMember();
		
		mv.addObject("memberList", memberList);
		mv.setViewName("admin/member");
		// 페이징바 해야 됨
		return mv;
	}
	
	@PostMapping("member/update")
	public String updateMember(Member member, int authorityCode, RedirectAttributes rttr, Locale locale) {
		
		adminService.updateMember(member, authorityCode);
		
		// 일회성 저장
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("updateMember", null, locale));
		
		return "redirect:/admin/member";
	}
	
	@GetMapping("report")
	public String adminReport() {
		return "admin/adminReport";
	}
	
	@GetMapping("camp")
	public String adminCamp() {
		return "admin/adminCamp";
	}
	
	@GetMapping("camp/detail")
	public String adminCampDetail() {
		return "admin/campDetail";
	}
}
