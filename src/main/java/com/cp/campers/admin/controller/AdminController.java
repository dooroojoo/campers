package com.cp.campers.admin.controller;

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

import com.cp.campers.admin.model.service.AdminService;
import com.cp.campers.admin.model.vo.Search;
import com.cp.campers.camp.model.vo.Camp;
import com.cp.campers.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;
@Slf4j
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

	/* 회원목록 */
	@GetMapping("member")
	public ModelAndView adminMember(ModelAndView mv) {
		
		int page = 1;
		
		Map<String, Object> map = adminService.findAllMember(page);
		
		mv.addObject("memberList", map.get("memberList"));
		mv.addObject("pi", map.get("pi"));
		mv.setViewName("admin/member");

		return mv;
	}
	
	/* 회원목록 + 페이징처리 */
	@GetMapping("memberPage")
	public String adminMemberPaging(Model model, int page) {
		
		Map<String, Object> map = adminService.findAllMember(page);
		
		model.addAttribute("memberList", map.get("memberList"));
		model.addAttribute("pi", map.get("pi"));

		return "admin/member";
	}
	
	/* 회원정보수정 */
	@PostMapping("member/update")
	public String updateMember(Member member, int authorityCode, RedirectAttributes rttr, Locale locale) {
		
		adminService.updateMember(member, authorityCode);
		
		// 일회성 저장
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("updateMember", null, locale));
		
		return "redirect:/admin/member";
	}
	
	/* 회원검색 */
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
	
	@GetMapping("report/modal")
	public String reportModal() {
		return "admin/reportModal";
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
	
	/* 숙소목록 + 페이징처리 */
	@GetMapping("campPage")
	public String adminCampPaging(Model model, int page) {
		
		Map<String, Object> map = adminService.findAllCamp(page);
		
		model.addAttribute("campList", map.get("campList"));
		model.addAttribute("pi", map.get("pi"));

		return "admin/camp";
	}
	
	/* 숙소검색 */
	@GetMapping("camp/search")
	public String campSearch(Search search, Model model) {
		
		int page = 1;
		
		Map<String, Object> map = adminService.fineCampBySearch(page, search);
		
		model.addAttribute("campList", map.get("campList"));
		model.addAttribute("pi", map.get("pi"));
		
		return "admin/camp";
	}
	
	/* 숙소검색 + 페이징 */
	@GetMapping("camp/searchPage")
	public String campSearchPaging(int page, Search search, Model model) {
		
		Map<String, Object> map = adminService.fineCampBySearch(page, search);
		
		model.addAttribute("campList", map.get("campList"));
		model.addAttribute("pi", map.get("pi"));
		
		return "admin/camp";
	}
	
	/* 숙소상세 */
	@GetMapping("camp/detail")
	public String adminCampDetail(int campNo, Model model) {
		
		String nlString = System.getProperty("line.separator").toString();
		
		Camp camp = adminService.detailCamp(campNo);
		
		log.info(camp.getCampName());
		
		model.addAttribute("camp", camp);
		model.addAttribute("newReply", "\n");
		
		return "admin/campDetail";
	}
	
	/* 숙소삭제 */
	@GetMapping("camp/delete")
	public String adminCampDelete(int campNo, Model model, RedirectAttributes rttr, Locale locale) {
		
		adminService.deleteCamp(campNo);
		
		// 일회성 저장
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("deleteCamp", null, locale));
		
		return "redirect:/admin/camp";
	}
	
}
