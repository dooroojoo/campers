package com.cp.campers.member.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cp.campers.member.model.service.MemberService;
import com.cp.campers.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	 public String signUp(@Value("${custom.path.upload-images}") String uploadFilesPath, Model model, @RequestParam MultipartFile singleFile,
             HttpServletRequest request,Member member) {
	      
	      /* 파일을 저장할 경로 */
		 //String filePath = "C:\\Users\\calls\\git\\campers\\src\\main\\resources\\static\\resources\\images\\uploadFiles\\profileImg";
	      
		 String filePath = uploadFilesPath + "/profileImg";
		 
	      File mkdir = new File(filePath);
	      if(!mkdir.exists()) mkdir.mkdir();
	      
	      /* 파일명 확인 */
	      String originFileName = singleFile.getOriginalFilename();

	      String ext = originFileName.substring(originFileName.lastIndexOf("."));
	      
	      String savedName = UUID.randomUUID().toString().replace("-", "") + ext;
	      
	      String email1 = request.getParameter("email1");
	      String email2 = request.getParameter("email2");
	      String phone1 = request.getParameter("phone1");
	      String phone2 = request.getParameter("phone2");
	      String phone3 = request.getParameter("phone3");
	      
	      member.setEmail(email1+"@"+email2);
	      member.setPhone(phone1+"-"+phone2+"-"+phone3);
	      try {
	         singleFile.transferTo(new File(filePath + "/" + savedName));
	         member.setProfilePath("/resources/images/uploadFiles/profileImg/"+savedName);
	         memberService.signUp(member);
	      } catch (IllegalStateException | IOException e) {
	         e.printStackTrace();
	      }
	      log.info("root = "+request.getSession().getServletContext().getRealPath("/"));
	      System.out.println("root"+request.getSession().getServletContext().getRealPath("/"));
	      
		 return "redirect:/";
	 }
	
}
