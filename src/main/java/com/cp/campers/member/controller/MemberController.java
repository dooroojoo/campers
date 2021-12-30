package com.cp.campers.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cp.campers.member.model.service.MemberService;
import com.cp.campers.member.model.vo.Member;

@Controller
@RequestMapping("/member")
public class MemberController {

	private MemberService memberService;
	
	 @Autowired
	   public MemberController(MemberService memberService) {
	      this.memberService = memberService;
	 }
	 
	 @GetMapping("/login")
	 public String loginForm() {
		 return "member/login";
	 }
	 
	 
	 @GetMapping("/signup")
	 public String signUpForm() {
		 return "member/signup";
	 }
	 
	 @PostMapping("/signup")
	 public String signUp(Member member) {
		 memberService.signUp(member);
		 
		 return "redirect:/";
	 }
	
}
