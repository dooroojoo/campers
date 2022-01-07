package com.cp.campers.mypage.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cp.campers.member.model.vo.Member;
import com.cp.campers.member.model.vo.UserImpl;
import com.cp.campers.mypage.model.service.MypageService;
import com.cp.campers.mypage.model.vo.Camp;
import com.cp.campers.mypage.model.vo.Room;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/mypage")
public class MyPageController {

	private MypageService mypageService;
	private MessageSource messageSource;
	
	 @Autowired
	   public MyPageController(MypageService mypageService, MessageSource messageSource) {	      
	     this.mypageService = mypageService;
	     this.messageSource = messageSource;
	 }
	
	 /* 회원 목록 */
	 @GetMapping("mypage")
	 public ModelAndView mypageMember(ModelAndView mv) {
				
		List<Member> memberList = mypageService.findAllMember();
		
		mv.addObject("memberList", memberList);
		mv.setViewName("/mypage");
		/*
		 * Map<String, Object> map = mypageService.findAllMember(page);
		 * 
		 * mv.addObject("memberList", map.get("memberList")); mv.addObject("pi",
		 * map.get("pi"));
		 */
		 
		 return mv;
	 }
	 	 	 
	 /* 마이페이지 */
	@GetMapping("")
	public String myPage() {
		return "mypage/mypage";
	}	
	
	/* 회원 정보 */
	@GetMapping("/changinfo") 
	public String changeInfo(@AuthenticationPrincipal UserImpl user, Model model) {
		model.addAttribute("user", user.getUserNo());
		return"mypage/changinfo"; 
	}
	
	/* 회원 탈퇴 */
	@GetMapping("/changinfo/changinfo_memberout") 
	public String changeInfoMemberout() {
		return"mypage/changinfo_memberout"; 
	}
	
	/* 회원탈퇴 입력 폼 */
	@PostMapping("/changinfo/changinfo_memberout")
	public String changeInfoMemberout(Member member, @AuthenticationPrincipal UserImpl user, 
								RedirectAttributes rttr, Model model){
		/* 유저 정보 가져오기 */
		member.setUserNo(user.getUserNo());
		
		member.setPwd(user.getPwd());
		
		return "redirect:/";
	}
	
	/* 닉네임 중복 체크 */
	@PostMapping("/nickName")
	@ResponseBody
	public int nickNameCheck(@RequestParam("nickName") String nickName) {
		log.info(nickName);
		int cnt = mypageService.nickNameCheck(nickName);
		
		return cnt;
	}
	
	/* 회원 정보 수정 */
	@GetMapping("/changinfo/changinfo_modify") 
	public String changeInfoModify() {
		return"mypage/changinfo_modify"; 
	}
	
	/* 회원 정보 수정 폼 */
	@PostMapping("/changinfo/changinfo_modify") 
	@ResponseBody
	public String changeInfoModify(Member member, @AuthenticationPrincipal UserImpl user, /*String email, 
			String phone, String nickName,*/ RedirectAttributes rttr, Locale locale) {
		member.setUserNo(user.getUserNo());
		member.setEmail(user.getEmail());
		member.setPhone(user.getPhone());
		member.setNickName(user.getNickName());
		
		/* changeInfoModify update */
		mypageService.changeInfoModify(member/*, email, phone, nickName*/);
		
		/* 메세지 */
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("changeInfoModify", null, locale));
		
		/* 로그 확인 */
		log.info(member.toString());
		
		/* 리다이렉트 */
		return"redirect:/mypage/changinfo"; 
	}	
	
	/* 회원 비밀번호 변경 */
	@GetMapping("/changinfo/changinfo_modify/changinfo_pwd_modify") 
	public String changeInfoPwdModify() {
		return"mypage/changinfo_pwd_modify"; 
	}
	
	/* 회원 비밀번호 변경 입력 폼 */
	@PostMapping("/changinfo/changinfo_modify/changinfo_pwd_modify")
	public String changeInfoPwdModify(Member member, String pwd, RedirectAttributes rttr, Locale locale) {
		
		mypageService.changeInfoPwdModify(member, pwd);
		
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("changeInfoPwdModify", null, locale));
		
		return "redirect:/mypage/changinfo";
	}
	
	/* 캠핑장 등록 페이지 */
	@GetMapping("/mypage_camp_enrollment") 
	public String mypageCampEnrollmentForm() {
				
		return"mypage/mypage_camp_enrollment"; 
	}
	
	/* 캠핑장 등록 입력폼 */
	/* @AuthenticationPrincipal UserImpl user 유저 정보 가져오기 */
	@PostMapping("/mypage_camp_enrollment")
	public String mypageCampEnrollment(Camp camp, 
							Room room, @AuthenticationPrincipal UserImpl user,
							@Value("${custom.path.upload-images}") String uploadFilesPath, Model model,
							@RequestParam List<MultipartFile> multiFiles,
							HttpServletRequest request,
							RedirectAttributes rttr, Locale locale) {
		/* 유저 정보 받아오기 */
		camp.setUserNo(user.getUserNo());
			
		/* 캠프 사진 파일 */
		// camp.setCampPath("test.png");
		/* 파일을 저장할 경로 */
		String filePath = uploadFilesPath + "/campImg";
		
		/* 해당 파일 경로 존재 여부 확인하여 없을 경우 make directory */
		File mkdir = new File(filePath);
		if(!mkdir.exists()) mkdir.mkdirs();
		
		/* 파일에 관한 정보 보관 */
		List<Map<String, String>> files = new ArrayList<>();
		
		/* List<MultipartFile> 반복문 */
		for(int i = 0; i < multiFiles.size(); i++) {
			/* 파일명 변경 처리 */
			String originFileName = multiFiles.get(i).getOriginalFilename();
			String ext = originFileName.substring(originFileName.lastIndexOf("."));
			String savedName = UUID.randomUUID().toString().replace("-", "") + ext;
			
			/* 파일에 관한 정보 추출 후 보관 */
			Map<String, String> file = new HashMap<>();
			file.put("originFileName", originFileName);
			file.put("savedName", savedName);
			file.put("filePath", filePath);
			
			files.add(file);
		}
		
		/* 파일 저장 */
		try {
			for(int i = 0; i < multiFiles.size(); i++) {
				Map<String, String> file = files.get(i);
				multiFiles.get(i).transferTo(new File(file.get("filePath") + "/" + file.get("savedName")));
				String originFileName = multiFiles.get(i).getOriginalFilename();
				String ext = originFileName.substring(originFileName.lastIndexOf("."));
				String savedName2 = UUID.randomUUID().toString().replace("-", "") + ext;
				camp.setCampPath("/resources/images/uploadFiles/campImg/" + savedName2);
				mypageService.mypageCampEnrollment(camp);
			} 
			
		} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			/* 실패 시 저장 된 파일 삭제 */
				for(int i = 0; i < multiFiles.size(); i++) {
					Map<String, String> file = files.get(i);
					new File(file.get(filePath) + "/" + file.get("savedName")).delete();
				}				
		}
				
		/* 숙소 등록 */
		camp.setRoom(room);
		
		/* 캠프, 캠프 타입, 시설 타입, 객실 등록한 로그 파일 받아오기 */
		log.info(camp.toString());
		mypageService.mypageCampEnrollment(camp);
		
		/* 숙소 등록 메세지 */
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("insertCamp", null, locale));
		
		/* 등록후 페이지 */
		return "redirect:/mypage";
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
