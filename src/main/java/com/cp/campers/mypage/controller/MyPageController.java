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

import com.cp.campers.camp.model.vo.ImageFile;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.member.model.vo.UserImpl;
import com.cp.campers.mypage.model.service.MypageService;
import com.cp.campers.mypage.model.vo.Camp;
import com.cp.campers.mypage.model.vo.CampFile;
import com.cp.campers.mypage.model.vo.Room;
import com.cp.campers.mypage.model.vo.RoomFile;

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
	 public ModelAndView mypageMember(ModelAndView mv, Model model) {
				
		List<Member> memberList = mypageService.findAllMember();
		
		mv.addObject("memberList", memberList);
		mv.setViewName("/mypage");
		
		/* board */
		int page = 1;
		
		Map<String, Object> map = mypageService.selectBoardList(page);
		
		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("pi",map.get("pi"));
		model.addAttribute("thumbnailList", map.get("thumbnailList"));
		
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
								RedirectAttributes rttr, Model model, Locale locale){
		/* 유저 정보 가져오기 */
		member.setUserNo(user.getUserNo());
		member.setId(user.getId());
		member.setPwd(user.getPwd());
		member.setStatus(user.getStatus());
		
		mypageService.changeInfoMemberout(member);
		
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("changeInfoMemberout", null, locale));
		
		return "redirect:/";
	}
	
	/* 닉네임 중복 체크 */
	@PostMapping("/nickName")
	@ResponseBody
	public String nickNameCheck(@RequestParam("nickName") String nickName) {
		log.info(nickName);
		int cnt = mypageService.nickNameCheck(nickName);
		
		if(cnt != 0) {
			return "fail";
		} else {
			return "success";
		}
		
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
		/*
		member.setUserNo(user.getUserNo());
		member.setEmail(user.getEmail());
		member.setPhone(user.getPhone());
		member.setNickName(user.getNickName());
		*/
		/* changeInfoModify update */
		mypageService.changeInfoModify(member/*, email, phone, nickName*/);
		
		/* 메세지 */
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("changeInfoModify", null, locale));
		
		/* 로그 확인 */
		// log.info(member.toString());
		
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
	public String mypageCampEnrollment(Camp camp, CampFile campFile, RoomFile roomFile,
							Room room, @AuthenticationPrincipal UserImpl user,
							@Value("${custom.path.upload-images}") String uploadFilesPath, Model model,
							@RequestParam MultipartFile singleFile,
							@RequestParam List<MultipartFile> campMultiFiles,
							@RequestParam List<MultipartFile> roomMultiFiles,
							HttpServletRequest request,
							RedirectAttributes rttr, Locale locale) {
		/* 유저 정보 받아오기 */
		camp.setUserNo(user.getUserNo());
				
		/* ------------------------사업장등록증-------------------------------- *
		log.info(uploadFilesPath);
		
		/* 사업자등록증 파일을 저장할 경로 */				
		String filePath = uploadFilesPath + "businessImg";
		
		/* 해당 파일 경로 존재 여부 확인하여 없을 경우 make directory */
		File mkdir = new File(filePath);
		if(!mkdir.exists()) mkdir.mkdirs();
		
		/* 사업장 등록증 파일명 확인*/
	    String originFileName = singleFile.getOriginalFilename();
	    /* 확장자 분리 */
	    String ext = originFileName.substring(originFileName.lastIndexOf("."));	      
	    /* 파일명 변경 처리 */
	    String savedName = UUID.randomUUID().toString().replace("-", "") + ext;
		System.out.println("savedName : " + savedName);
		
	    
	    /* 파일을 저장함 */
		try {
			singleFile.transferTo(new File(filePath + "/" + savedName));
			camp.setCampPath("/resources/images/uploadFiles/businessImg/"+savedName);
			//camp.setCampPath(filePath + "/businessImg");
			//camp.setCampPath(filePath);
			
			/* 오류 이유!!! */
			//mypageService.mypageCampEnrollment(camp);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		log.info("root = "+request.getSession().getServletContext().getRealPath("/"));
 		/*-------------------------------------------------------------------------*/		
		
		/* --------------------------- 사업장 사진 ------------------------------*/
		/* 멀티파일 아직 해결 못함 
		 * 사업장 사진, 객실 사진 주석처리 하면 사업장등록증 까진 insert 가능. */
		
		/* 사업장 사진을 저장할 경로 */
		String campFilePath = uploadFilesPath + "campImg";
		/* 해당 파일 경로 존재 여부 확인하여 없을 경우 make directory */
		File mkdir2 = new File(filePath);
		if(!mkdir2.exists()) mkdir2.mkdirs();
		
		/* 사업장 사진 파일에 관한 정보 보관 */		
		List<Map<String, String>> files2 = new ArrayList<>();
		
		/* List<MultipartFile> 반복문 */
		for(int i = 0; i < campMultiFiles.size(); i++) {
			/* 파일명 변경 처리 */
			String originFileName2 = campMultiFiles.get(i).getOriginalFilename();
			String ext2 = originFileName2.substring(originFileName2.lastIndexOf("."));
			String savedName2 = UUID.randomUUID().toString().replace("-", "") + ext2;
			
			/* 파일에 관한 정보 추출 후 보관 */
			Map<String, String> file2 = new HashMap<>();
			file2.put("originFileName2", originFileName2);
			file2.put("savedName2", savedName2);
			file2.put("campFilePath", campFilePath);
			
			files2.add(file2);
		}
		
		/* 파일 저장 */
		try {
			for(int i = 0; i < campMultiFiles.size(); i++) {
				Map<String, String> file2 = files2.get(i);
				campMultiFiles.get(i).transferTo(new File(file2.get("campFilePath") + "/" + file2.get("savedName2")));
				String originFileName2 = campMultiFiles.get(i).getOriginalFilename();
				String ext2 = originFileName2.substring(originFileName2.lastIndexOf("."));
				String savedName2 = UUID.randomUUID().toString().replace("-", "") + ext2;
				/* 주소 전송 참고
				 * camp.setCampPath("/resources/images/uploadFiles/businessImg/"+savedName);
				 * */
				camp.setCampFile(campFile);
				
				//camp.setCampFile("/resources/images/uploadFiles/campImg/" + savedName2);
				// mypageService.mypageCampEnrollment(camp);
			} 
			
		} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			/* 실패 시 저장 된 파일 삭제 */
				for(int i = 0; i < campMultiFiles.size(); i++) {
					Map<String, String> file2 = files2.get(i);
					new File(file2.get(campFilePath) + "/" + file2.get("savedName")).delete();
				}				
		}	
	   
		/* --------------------------- 객실 ------------------------------*/
		
		/* 객실 사진을 저장할 경로 */
		String roomFilePath = uploadFilesPath + "roomImg";
		/* 해당 파일 경로 존재 여부 확인하여 없을 경우 make directory */
		File mkdir3 = new File(filePath);
		if(!mkdir3.exists()) mkdir3.mkdirs();						
		
		/* 객실 사진 파일에 관한 정보 보관 */
		List<Map<String, String>> files3 = new ArrayList<>();		
		
		/* List<MultipartFile> 반복문 */
		for(int i = 0; i < roomMultiFiles.size(); i++) {
			/* 파일명 변경 처리 */
			String originFileName3 = roomMultiFiles.get(i).getOriginalFilename();
			String ext3 = originFileName3.substring(originFileName3.lastIndexOf("."));
			String savedName3 = UUID.randomUUID().toString().replace("-", "") + ext3;
			
			/* 파일에 관한 정보 추출 후 보관 */
			Map<String, String> file3 = new HashMap<>();
			file3.put("originFileName3", originFileName3);
			file3.put("savedName3", savedName3);
			file3.put("roomFilePath", roomFilePath);
			
			files3.add(file3);
		}
		
		/* 파일 저장 */
		try {
			for(int i = 0; i < roomMultiFiles.size(); i++) {
				Map<String, String> file3 = files3.get(i);
				roomMultiFiles.get(i).transferTo(new File(file3.get("roomFilePath") + "/" + file3.get("savedName")));
				String originFileName3 = roomMultiFiles.get(i).getOriginalFilename();
				String ext3 = originFileName3.substring(originFileName3.lastIndexOf("."));
				String savedName3 = UUID.randomUUID().toString().replace("-", "") + ext3;
				//camp.setCampPath("/resources/images/uploadFiles/roomImg/" + savedName3);
				camp.setRoomFile(roomFile);
				mypageService.mypageCampEnrollment(camp);
			} 
			
		} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			/* 실패 시 저장 된 파일 삭제 */
				for(int i = 0; i < roomMultiFiles.size(); i++) {
					Map<String, String> file3 = files3.get(i);
					new File(file3.get(roomFilePath) + "/" + file3.get("savedName")).delete();
				}				
		}
		
		
		
		/* 캠프 사진 파일 */
		//camp.setCampPath("filePath");
		
		/* 숙소 등록 */
		camp.setRoom(room);
		log.info(room.toString());
		/* 숙소 사진 등록 */
		//camp.setCampFile(campFile);
		/* 객실 사진 등록 */
		//camp.setRoomFile(roomFile);
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
