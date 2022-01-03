package com.cp.campers.admin.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cp.campers.admin.model.vo.Search;
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

	/* 회원 목록 */
	@GetMapping("member")
	public ModelAndView adminMember(ModelAndView mv) {
		
		int page = 1;
		
		Map<String, Object> map = adminService.findAllMember(page);
		
		mv.addObject("memberList", map.get("memberList"));
		mv.addObject("pi", map.get("pi"));
		mv.setViewName("admin/member");

		return mv;
	}
	
	/* 회원 목록 + 페이징처리 */
	@GetMapping("memberPage")
	public String adminMemberpaging(Model model, int page) {
		
		Map<String, Object> map = adminService.findAllMember(page);
		
		model.addAttribute("memberList", map.get("memberList"));
		model.addAttribute("pi", map.get("pi"));

		return "admin/member";
	}
	
	/* 회원정보 수정 */
	@PostMapping("member/update")
	public String updateMember(Member member, int authorityCode, RedirectAttributes rttr, Locale locale) {
		
		adminService.updateMember(member, authorityCode);
		
		// 일회성 저장
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("updateMember", null, locale));
		
		return "redirect:/admin/member";
	}
	
	/* 회원 검색 */
	@GetMapping("member/search")
	public String seachMember(Search search, Model model) {
	
		int page = 1;
		
		Map<String, Object> map = adminService.searchMember(page, search);
		model.addAttribute("memberList", map.get("memberList"));
		model.addAttribute("pi", map.get("pi"));
		
		return "admin/member";
	}
	
	/* 신고목록 */
	@GetMapping("report")
	public String adminReport() {
		return "admin/report";
	}
	
	/* 숙소목록 */
	@GetMapping("camp")
	public String adminCamp(Model model) {
		
		int page = 1;
		
		Map<String, Object> map = adminService.findAllCamp(page);
		
		model.addAttribute("campList", map.get("campList"));
		model.addAttribute("pi", map.get("pi"));
		
		return "admin/camp";
	}
	
	/* 숙소신청 */
	@GetMapping("camp/detail")
	public String adminCampDetail() {
		return "admin/campDetail";
	}
}
