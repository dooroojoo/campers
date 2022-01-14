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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import com.cp.campers.board.model.vo.Attachment;
import com.cp.campers.board.model.vo.Board;
import com.cp.campers.member.model.service.MemberService;
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
	 @GetMapping("")
	 public ModelAndView mypageMember(Member member, Board board, ModelAndView mv, @AuthenticationPrincipal UserImpl user) {
		
		member.setUserNo(user.getUserNo());
		//board.setWriter(user.getUserNo());
		
		List<Member> memberList = mypageService.findAllMember();
		//List<Board> boardList = mypageService.findAllBoard();
		
		
		mv.addObject("memberList", memberList);
		//mv.addObject("boardList", boardList);
		mv.setViewName("mypage/mypage");
		
		/*
		// board
		int writer = loginUser.getUserNo();
		
		log.info("page="+page);
		
		//int page = 1;
		
		Map<String, Object> map = mypageService.selectMyBoardList(writer,page);
		
		model.addAttribute("boardList",map.get("boardList"));
		model.addAttribute("pi",map.get("pi"));
		model.addAttribute("thumbnailList", map.get("thumbnailList"));
		*/		
		
		return mv;
	 }	 
	
	/* 회원 정보 */
	@GetMapping("/changinfo") 
	public String changeInfo(@AuthenticationPrincipal UserImpl user, Model model) {
				
		model.addAttribute("user", user.getUserNo());
		return"mypage/changinfo"; 
	}
	
	/* 회원 탈퇴 */
	@GetMapping("/changinfo/changinfoMemberout") 
	public String changeInfoMemberout() {
		return"mypage/changinfo_memberout"; 
	}
	
	/* 회원탈퇴 입력 폼 */
	@PostMapping("/changinfo/changinfoMemberout")
	public String changeInfoMemberout(Member member, @AuthenticationPrincipal UserImpl user, 
								RedirectAttributes rttr, Model model, Locale locale){
		/* 유저 정보 가져오기 */
		member.setUserNo(user.getUserNo());
		member.setId(user.getId());
		member.setPwd(user.getPwd());
		member.setStatus(user.getStatus());
				
		// log.info("유저 아이디 : ", user);
		
		mypageService.changeInfoMemberout(member);
		
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("changeInfoMemberout", null, locale));
		
		return "mypage/changinfo";
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
	@GetMapping("/changinfo/changinfoModify") 
	public String changeInfoModify() {
		return"mypage/changinfo_modify"; 
	}
		
	/* 회원 정보 수정 폼 */
	@PostMapping("/changinfo/changinfoModify/update") /*String email, String phone, String nickName,*/
	public String changeInfoModify(Member member, @AuthenticationPrincipal UserImpl user, RedirectAttributes rttr, Locale locale) {
		
		// 유저 정보 가져오기
		member.setUserNo(user.getUserNo());
		member.setId(user.getId());
		
		member = mypageService.changeInfoModify(member);
				
		// 메세지		
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("changeInfoModify", null, locale));
		
		
		user.setDetails(member);
	
		
		// 리다이렉트
		return "redirect:/mypage/changinfo"; 
	}		
	
	/* 회원 비밀번호 변경 */
	@GetMapping("/changinfo/changinfoModify/changinfoPwdModify") 
	public String changeInfoPwdModify() {
		return"mypage/changinfo_pwd_modify"; 
	}
	
	/* 회원 비밀번호 변경 입력 폼 */
	@PostMapping("/changinfo/changinfoModify/changinfoPwdModify")
	public String changeInfoPwdModify(Member member, @AuthenticationPrincipal UserImpl user,
			@RequestParam("pwd")String pwd , @RequestParam("newPwd")String newPwd ,
			@RequestParam("newPwd2")String newPwd2 ,RedirectAttributes rttr, Locale locale) {
		
		// 유저 정보 가져오기
		member.setUserNo(user.getUserNo());
		member.setId(user.getId());
		member.setPwd(user.getPwd());
		
		
		
		mypageService.changeInfoPwdModify(member);
		
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("changeInfoPwdModify", null, locale));
		
		user.setDetails(member);
		
		return "redirect:/mypage/changinfo";
	}
	
	/* 캠핑장 등록 페이지 */
	@GetMapping("/mypageCampEnrollment") 
	public String mypageCampEnrollmentForm() {
				
		return "mypage/mypage_camp_enrollment"; 
	}
	
	@GetMapping("/mypageCampEnrollmentRoom")
	public String mypage_camp_enrollment_roomForm() {
		return "mypage/mypage_camp_enrollment_room";
	}
	
	/* 숙소 등록 */
	@PostMapping("/mypageCampEnrollmentRoom")
	public String mypage_camp_enrollment_room(Camp camp, Room room, @AuthenticationPrincipal UserImpl user,
			@Value("${custom.path.upload-images}") String uploadFilesPath, Model model,
			@RequestParam List<MultipartFile> roomMultiFiles,
			HttpServletRequest request, RedirectAttributes rttr, Locale locale) {
		
	camp.setUserNo(user.getUserNo());
	
	/* 숙소 등록 */
	camp.setRoom(room);
	
	/* 캠프, 캠프 타입, 시설 타입, 객실 등록한 로그 파일 받아오기 */
	log.info(camp.toString());
	mypageService.mypage_camp_enrollment_room(camp);
	
	/* 숙소 등록 메세지 */
	rttr.addFlashAttribute("successMessage", messageSource.getMessage("insertCamp", null, locale));
	
		
		return "redirect:/mypage/mypage_camp_management";
	}
	
	/* 캠핑장 등록 입력폼 */
	/* @AuthenticationPrincipal UserImpl user 유저 정보 가져오기 */
	@PostMapping("/mypageCampEnrollment")
	public String mypageCampEnrollment(Camp camp, CampFile campFile, RoomFile roomFile,
							Room room, @AuthenticationPrincipal UserImpl user,
							@Value("${custom.path.upload-images}") String uploadFilesPath, Model model,
							@RequestParam MultipartFile singleFile,
							@RequestParam MultipartFile[] campMultiFiles,
							@RequestParam MultipartFile[] roomMultiFiles,
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
		String campFilePath = uploadFilesPath + "/campImg";
		
		/* 해당 파일 경로 존재 여부 확인하여 없을 경우 make directory */
		File mkdir2 = new File(campFilePath);
		if(!mkdir2.exists()) mkdir2.mkdirs();
		
		int campNo = 0;
		/* 사업장 사진 파일에 관한 정보 보관 */		
		List<Map<String, String>> files = new ArrayList<>();
		List<MultipartFile> campMultiFileList = new ArrayList<>();
		
		for(int i = 0; i < campMultiFiles.length; i++) {
			if(campMultiFiles[i].getSize() != 0) {
				campMultiFileList.add(campMultiFiles[i]);
			}
		}
		
		/* List<MultipartFile> 반복문 */
		for(int i = 0; i < campMultiFileList.size(); i++) {
			/* 파일명 변경 처리 */
			//String originFileName2 = campMultiFileList.get(i).getOriginalFilename();
			//String ext2 = originFileName2.substring(originFileName2.lastIndexOf("."));
			//String savedName2 = UUID.randomUUID().toString().replace("-", "") + ext2;
						
			/* 파일에 관한 정보 추출 후 보관 */
			Map<String, String> file = new HashMap<>();
			file.put("originFileName", originFileName);
			file.put("savedName", savedName);
			file.put("campFilePath", campFilePath);			
			files.add(file);
		}
		
		/* 파일 저장 */
		try {
		
			for(int i = 0; i < campMultiFileList.size(); i++) {
				
				Map<String, String> file = files.get(i);
				campMultiFileList.get(i).transferTo(new File(file.get("campFilePath") + "\\" + file.get("savedName")));
				
				Attachment attachment = new Attachment();
				attachment.setFileName(file.get("savedName"));
				attachment.setFileOriginName(file.get("originFileNmae"));
				attachment.setFileOriginName("originFileNmae");
				attachment.setFileRoute("/resources/images/uploadFiles/campImg/");
				
				if(i == 0)
					attachment.setFileLevel(0);
				else
					attachment.setFileLevel(1);
				
				mypageService.insertCampImage(attachment);
				
				} 
			campNo = mypageService.selectCampNo();
		} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			/* 실패 시 저장 된 파일 삭제 */
				for(int i = 0; i < campMultiFileList.size(); i++) {
					
					Map<String, String> file = files.get(i);
					new File(file.get("campFilePath") + "\\" + file.get("savedName")).delete();
				}				
		}	
	   
		/* --------------------------- 객실 ------------------------------*/
		
		/* 객실 사진을 저장할 경로 */
		String roomFilePath = uploadFilesPath + "/roomImg";
		
		/* 해당 파일 경로 존재 여부 확인하여 없을 경우 make directory */
		File mkdir3 = new File(roomFilePath);
		if(!mkdir3.exists()) mkdir3.mkdirs();						
		
		/* 객실 사진 파일에 관한 정보 보관 */
		List<Map<String, String>> files3 = new ArrayList<>();
		List<MultipartFile> roomMultiFileList = new ArrayList<>();
				
		for(int i = 0; i < roomMultiFiles.length; i++) {
			if(roomMultiFiles[i].getSize() != 0) {
				roomMultiFileList.add(roomMultiFiles[i]);
			}
		}		
		
		/* List<MultipartFile> 반복문 */
		for(int i = 0; i < roomMultiFileList.size(); i++) {
			/* 파일명 변경 처리 */
			String originFileName3 = roomMultiFileList.get(i).getOriginalFilename();
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
			for(int i = 0; i < roomMultiFileList.size(); i++) {
				Map<String, String> file3 = files3.get(i);
				roomMultiFileList.get(i).transferTo(new File(file3.get("roomFilePath") + "/" + file3.get("savedName")));
				
				Attachment atta2 = new Attachment();
				atta2.setFileName(file3.get("savedName3"));
				atta2.setFileOriginName(file3.get("originFileNmae"));
				atta2.setFileRoute("/resources/images/uploadFiles/roomImg/");
				
				if(i == 0)
					atta2.setFileLevel(0);
				else
					atta2.setFileLevel(1);
				
				mypageService.insertRoomImage(atta2);
			} 
			
		} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			/* 실패 시 저장 된 파일 삭제 */
				for(int i = 0; i < roomMultiFileList.size(); i++) {
					Map<String, String> file3 = files3.get(i);
					new File(file3.get(roomFilePath) + "\\" + file3.get("savedName")).delete();
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
	@GetMapping("/mypageCampManagementOut") 
	public String mypageCampManagementOut() {
		return"mypage/mypage_camp_management_out"; 
	}
	
	/* 캠핑장 관리(사업자용) */
	@GetMapping("/mypageCampManagement") 
	public String mypageCampManagement() {
		return"mypage/mypage_camp_management"; 
	}
	
	/* 마이페이지 카테고리 */
	@GetMapping("/mypageCategory") 
	public String mypageCategory() {
		return"mypage/mypage_category"; 
	}
	
	/* 회원 예약 내역 */
	@GetMapping("/mypageGuestReserve") 
	public String mypageGuestReserve() {
		return"mypage/mypage_guest_reserve"; 
	}
	
	/* 사업자 예약 내역 */
	@GetMapping("/mypageHostReserve") 
	public String mypageHostReserve() {
		return"mypage/mypage_host_reserve"; 
	}
	
	/* 찜한 페이지 */
	@GetMapping("/wishcamp") 
	public String wishCamp() {
		return"mypage/wishCamp"; 
	}
	
}
