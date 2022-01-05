package com.cp.campers.mypage.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cp.campers.member.model.service.MemberService;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.mypage.model.service.MypageService;
import com.cp.campers.mypage.model.vo.Camp;


@Controller
@RequestMapping("/mypage")
public class MyPageController {

	private MypageService mypageService;
	private MemberService memberService;
	
	 @Autowired
	   public MyPageController(MypageService mypageService, MemberService memberService) {	      
	      this.mypageService = mypageService;
	      this.memberService = memberService;
	 }
	
	 /* 회원 목록 */
	 @GetMapping("mypage")
	 public ModelAndView mypageMember(ModelAndView mv, Model model) {
		 
		int page = 1;
		
		List<Member> member = mypageService.findMember();
		model.addAttribute("member", member);
		Map<String, Object> map = mypageService.findAllMember(page);
			
		mv.addObject("memberList", map.get("memberList"));
		mv.addObject("pi", map.get("pi"));
		mv.setViewName("/");
		 
		 return mv;
	 }
	 	 	 
	 /* 마이페이지 */
	@GetMapping("")
	public String myPage() {
		return "mypage/mypage";
	}	
	
	/* 회원 정보 */
	@GetMapping("/changinfo") 
	public String changeInfo() {
		return"mypage/changinfo"; 
	}
	
	/* 회원 탈퇴 */
	@GetMapping("/changinfo/changinfo_memberout") 
	public String changeInfoMemberout() {
		return"mypage/changinfo_memberout"; 
	}
	
	/* 회원 정보 수정*/
	@GetMapping("/changinfo/changinfo_modify") 
	public String changeInfoModify() {
		return"mypage/changinfo_modify"; 
	}
	
	/* 회원 비밀번호 변경 */
	@GetMapping("/changinfo/changinfo_pwd_modify") 
	public String changeInfoPwdModify() {
		return"mypage/changinfo_pwd_modify"; 
	}
	
	/* 캠핑장 등록 페이지 */
	@GetMapping("/mypage_camp_enrollment") 
	public String mypageCampEnrollmentForm() {
		return"mypage/mypage_camp_enrollment"; 
	}
	
	/* 캠핑장 등록 입력폼 */
	@PostMapping("/mypage_camp_enrollment")
	public String mypageCampEnrollment(Camp camp) {
		camp.setCampPath("test.png");
		mypageService.mypageCampEnrollment(camp);
		return "redirect:/";
	}	
	
	/* 캠핑장 해지 */
	@GetMapping("/mypage_camp_management_out") 
	public String mypageCampManagementOut() {
		return"mypage/mypage_camp_management_out"; 
	}
	
	/* 캠핑장 관리(사업자용) */
	@GetMapping("/mypage_camp_management") 
	public String mypageCampManagement() {
		return"mypage/mypage_camp_management"; 
	}
	
	/* 마이페이지 카테고리 */
	@GetMapping("/mypage_category") 
	public String mypageCategory() {
		return"mypage/mypage_category"; 
	}
	
	/* 회원 예약 내역 */
	@GetMapping("/mypage_guest_reserve") 
	public String mypageGuestReserve() {
		return"mypage/mypage_guest_reserve"; 
	}
	
	/* 사업자 예약 내역 */
	@GetMapping("/mypage_host_reserve") 
	public String mypageHostReserve() {
		return"mypage/mypage_host_reserve"; 
	}
	
	/* 찜한 페이지 */
	@GetMapping("/wishcamp") 
	public String wishCamp() {
		return"mypage/wishCamp"; 
	}
	
}
