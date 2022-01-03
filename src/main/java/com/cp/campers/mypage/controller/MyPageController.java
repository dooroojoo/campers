package com.cp.campers.mypage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cp.campers.mypage.model.service.MypageService;
import com.cp.campers.mypage.model.vo.Camp;


@Controller
@RequestMapping("/mypage")
public class MyPageController {

	private MypageService mypageService;
	
	 @Autowired
	   public MyPageController(MypageService mypageService) {	      
	      this.mypageService = mypageService;
	 }
	
	@GetMapping("")
	public String myPage() {
		return "mypage/mypage";
	}	
	
	@GetMapping("/changinfo") 
	public String changeInfo() {
		return"mypage/changinfo"; 
	}
	
	@GetMapping("/changinfo/changinfo_memberout") 
	public String changeInfoMemberout() {
		return"mypage/changinfo_memberout"; 
	}
	
	@GetMapping("/changinfo/changinfo_modify") 
	public String changeInfoModify() {
		return"mypage/changinfo_modify"; 
	}
	
	@GetMapping("/changinfo/changinfo_pwd_modify") 
	public String changeInfoPwdModify() {
		return"mypage/changinfo_pwd_modify"; 
	}
	
	@GetMapping("/mypage_camp_enrollment") 
	public String mypageCampEnrollmentForm() {
		return"mypage/mypage_camp_enrollment"; 
	}
	
	@PostMapping("/mypage_camp_enrollment")
	public String mypageCampEnrollment(Camp camp) {
		mypageService.mypageCampEnrollment(camp);
		
		return "redirect:/";
	}	
	
	@GetMapping("/mypage_camp_management_out") 
	public String mypageCampManagementOut() {
		return"mypage/mypage_camp_management_out"; 
	}
	
	@GetMapping("/mypage_camp_management") 
	public String mypageCampManagement() {
		return"mypage/mypage_camp_management"; 
	}
	
	@GetMapping("/mypage_category") 
	public String mypageCategory() {
		return"mypage/mypage_category"; 
	}
	
	@GetMapping("/mypage_guest_reserve") 
	public String mypageGuestReserve() {
		return"mypage/mypage_guest_reserve"; 
	}
	
	@GetMapping("/mypage_host_reserve") 
	public String mypageHostReserve() {
		return"mypage/mypage_host_reserve"; 
	}
	
	@GetMapping("/wishcamp") 
	public String wishCamp() {
		return"mypage/wishCamp"; 
	}
	
}
