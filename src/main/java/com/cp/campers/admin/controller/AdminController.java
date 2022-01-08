package com.cp.campers.admin.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cp.campers.admin.model.service.AdminService;
import com.cp.campers.admin.model.vo.Report;
import com.cp.campers.admin.model.vo.Search;
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
	public String searchMember(Search search, Model model) {
	
		int page = 1;
		
		Map<String, Object> map = adminService.searchMember(page, search);
		model.addAttribute("memberList", map.get("memberList"));
		model.addAttribute("pi", map.get("pi"));
		
		if (map.get("memberList").toString().equals("[]")) {
			model.addAttribute("noResult", "검색된 회원이 없습니다.");
		}
		
		return "admin/member";
	}
	
	/* 신고목록 */
	@GetMapping("report")
	public String adminReport() {
		return "admin/report";
	}
	
	@PostMapping("report")
	@ResponseBody
	public Map<String, String> insertReport(@RequestBody Report report) {
		
		log.info("입력 요청 report : {}", report);
		
		String msg = adminService.insertReport(report) > 0 ? "신고 처리가 완료되었습니다 !" : "신고 처리가 실패되었습니다 !";
		
		Map<String, String> map = new HashMap<>();
		map.put("msg", msg);
		
		return map;
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
		
		Map<String, Object> map = adminService.findCampBySearch(page, search);
		
		model.addAttribute("campList", map.get("campList"));
		model.addAttribute("pi", map.get("pi"));
		
		if (map.get("campList").toString().equals("[]")) {
			model.addAttribute("noResult", "검색된 숙소가 없습니다.");
		}
		log.info(map.get("pi").toString());
		return "admin/camp";
	}
	
	/* 숙소검색 + 페이징 */
	@GetMapping("camp/searchPage")
	public String campSearchPaging(int page, Search search, Model model) {
		
		Map<String, Object> map = adminService.findCampBySearch(page, search);
		
		model.addAttribute("campList", map.get("campList"));
		model.addAttribute("pi", map.get("pi"));
		
		return "admin/camp";
	}
	
	/* 숙소상세 */
	@GetMapping("camp/detail")
	public String adminCampDetail(int campNo, Model model) {
		
		Map<String, Object> map = adminService.detailCamp(campNo);
		// log.info(map.get("roomList").toString());
		model.addAttribute("camp", map.get("camp"));
		model.addAttribute("roomList", map.get("roomList"));
		model.addAttribute("newReply", '\n');
		
		return "admin/campDetail";
	}
	
	/* 숙소삭제 */
	@GetMapping("camp/delete")
	public String adminCampDelete(int campNo, int userNo, RedirectAttributes rttr, Locale locale) {

		adminService.deleteCamp(campNo, userNo);
		
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("deleteCamp", null, locale));
		
		return "redirect:/admin/camp";
	}
	
	/* 숙소거절 */
	@PostMapping("camp/refusal")
	public String adminCampRefusal(int campNo, int userNo, String refusal, RedirectAttributes rttr, Locale locale) {
		log.info("refusal : " + refusal);
		log.info("campNo : " + campNo);
		log.info("userNo : " + userNo);
		
		adminService.refusal(campNo, userNo, refusal);
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("refusal", null, locale));
		
		return "redirect:/admin/camp";
	}
	
	/* 숙소등록 */
	@GetMapping("/camp/enroll")
	public String adminCampEnroll(int campNo, int userNo, RedirectAttributes rttr, Locale locale) {
		log.info("campNo : " + campNo);
		log.info("userNo : " + userNo);
		
		adminService.enroll(campNo, userNo);
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("enroll", null, locale));
		
		return "redirect:/admin/camp";
	}
}
