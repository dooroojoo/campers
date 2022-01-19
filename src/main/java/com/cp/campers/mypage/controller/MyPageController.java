package com.cp.campers.mypage.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cp.campers.admin.model.service.AdminService;
import com.cp.campers.admin.model.vo.CampRecord;
import com.cp.campers.board.model.service.BoardService;
import com.cp.campers.board.model.vo.Attachment;
import com.cp.campers.board.model.vo.Board;
import com.cp.campers.camp.model.service.CampService;
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

	private MessageSource messageSource;
	private MypageService mypageService;
	private BoardService boardService;
	private AdminService adminService;
	private CampService campService;

	@Autowired
	public MyPageController(MessageSource messageSource, MypageService mypageService, BoardService boardService,
			AdminService adminService, CampService campService) {
		this.messageSource = messageSource;
		this.mypageService = mypageService;
		this.boardService = boardService;
		this.adminService = adminService;
		this.campService = campService;
	}

	/* 회원 목록 */
	/*
	 * 관리자 admin password : admin1! 회원 user12345 password: user12345!
	 */
	@GetMapping("")
	public ModelAndView mypageMember(Model model, Member member, Board board, ModelAndView mv,
			@AuthenticationPrincipal UserImpl user) {

		int writer = user.getUserNo();
		int userNo = user.getUserNo();

		int page = 1;

		Map<String, Object> map = mypageService.selectMyBoardList(writer, page);

		// 보류
		Map<String, Object> map2 = mypageService.selectMyMemberList(userNo, page);
		//log.info("map2 : " + map2.toString());

		Date today = new Date();

		//log.info("date : " + today);

		mv.addObject("boardList", map.get("boardList"));
		mv.addObject("pi", map.get("pi"));
		mv.addObject("memberList", map2.get("memberList"));
		mv.addObject("campList", map.get("campList"));

		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("memberList", map2.get("memberList"));
		model.addAttribute("pi", map.get("pi"));
		model.addAttribute("standardDate", new Date());
		//model.addAttribute("thumbnailList", map.get("thumbnailList"));
		mv.setViewName("mypage/mypage");

		return mv;
	}

	/* 마이페이지 프로필 사진 변경 */
	@PostMapping("")
	public String mypageMember(@Value("${custom.path.upload-images}") String uploadFilesPath,
			@RequestParam MultipartFile singleFile, Model model, Member member, @AuthenticationPrincipal UserImpl user,
			RedirectAttributes rttr, Locale locale) {

		member.setUserNo(user.getUserNo());
		// member.setProfilePath(user.getProfilePath());
		member.setId(user.getId());

		log.info("Post member : " + member.toString());
		log.info("Post user : " + user.toString());

		if (singleFile.getSize() != 0) {
			String filePath = uploadFilesPath + "/profileImg";

			File mkdir = new File(filePath);
			if (!mkdir.exists())
				mkdir.mkdir();

			String originFileName = singleFile.getOriginalFilename();

			String ext = originFileName.substring(originFileName.lastIndexOf("."));

			String savedName = UUID.randomUUID().toString().replace("-", "") + ext;

			try {
				singleFile.transferTo(new File(filePath + "/" + savedName));
				member.setProfilePath("/resources/images/uploadFiles/profileImg/" + savedName);
				log.info("try member : " + member.toString());
				// mypageService.updateProfilePath(member);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		} /*
			 * else { member.setProfilePath("null"); log.info("else member : " +
			 * member.toString()); mypageService.updateProfilePath(member); }
			 */
		mypageService.updateProfilePath(member);
		log.info("마지막 member : " + member.toString());

		rttr.addFlashAttribute("successMessage", messageSource.getMessage("mypageProfile", null, locale));
		
		// 리다이렉트
		return "redirect:/mypage";
	}

	/* 회원 정보 */
	@GetMapping("/changinfo")
	public String changeInfo(@AuthenticationPrincipal UserImpl user, Model model) {
		int userNo = user.getUserNo();
		int page = 1;
		Map<String, Object> map2 = mypageService.selectMyMemberList(userNo, page);

		model.addAttribute("memberList", map2.get("memberList"));

		log.info(user.toString());
		log.info(map2.toString());
		model.addAttribute("user", user.getUserNo());
		return "mypage/changinfo";
	}

	/* 회원 탈퇴 */
	@GetMapping("/changinfo/changinfoMemberout")
	public String changeInfoMemberout() {
		return "mypage/changinfo_memberout";
	}

	/* 회원탈퇴 입력 폼 */
	@PostMapping("/changinfo/changinfoMemberout")
	@ResponseBody
	public String changeInfoMemberout(Model model, Member member,
			RedirectAttributes rttr, Locale locale) {
		
		log.info("@@@@@@@@member="+member);
				
		int result = mypageService.changeInfoMemberout(member);
			
		String message = "";
		if(result > 0) {
			message = "success";
		}else {
			message = "fail";
		}
		
		//log.info(member.getId(), member.getPwd());
		//log.info("회원탈퇴 member : " + member.toString());
	
		//mypageService.changeInfoMemberout(member);

		//rttr.addFlashAttribute("successMessage", messageSource.getMessage("changeInfoMemberout", null, locale));

		//return mypageService.deleteMember(member);
		return message;		
	}

	/* 닉네임 중복 체크 */
	@PostMapping("/nickName")
	@ResponseBody
	public String nickNameCheck(@RequestParam("nickName") String nickName) {
		log.info(nickName);
		int cnt = mypageService.nickNameCheck(nickName);

		if (cnt != 0) {
			return "fail";
		} else {
			return "success";
		}

	}

	/* 회원 정보 수정 */
	@GetMapping("/changinfo/changinfoModify")
	public String changeInfoModify(@AuthenticationPrincipal UserImpl user, Model model) {

		int userNo = user.getUserNo();
		int page = 1;
		Map<String, Object> map2 = mypageService.selectMyMemberList(userNo, page);

		model.addAttribute("memberList", map2.get("memberList"));

		log.info("user : " + user.toString());
		log.info("map2 : " + map2.toString());
		model.addAttribute("user", user.getUserNo());

		return "mypage/changinfo_modify";
	}

	/* 회원 정보 수정 폼 */
	@PostMapping("/changinfo/changinfoModify/update") /* String email, String phone, String nickName, */
	public String changeInfoModify(Model model, Member member, @AuthenticationPrincipal UserImpl user,
			RedirectAttributes rttr, Locale locale) {

		// 유저 정보 가져오기
		member.setUserNo(user.getUserNo());
		member.setId(user.getId());
		log.info("update : " + user.toString());
		log.info("member : " + member.toString());

		member = mypageService.changeInfoModify(member);

		log.info(member.toString());

		// 메세지
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("changeInfoModify", null, locale));

		user.setDetails(member);

		// 리다이렉트
		return "redirect:/mypage/changinfo";
	}

	/* 회원 비밀번호 변경 */
	@GetMapping("/changinfo/changinfoModify/changinfoPwdModify")
	public String changeInfoPwdModify() {
		return "mypage/changinfo_pwd_modify";
	}
	
	 /* 비밀번호 체크 */	
	 @PostMapping("/pwdCheck")	 
	 @ResponseBody /*Member member, Model model*/
	 public String pwdCheck(Member member, @RequestBody String pwd, HttpSession session) {	 
	 /*member에 id를 가져와서 userPwd로 비밀번호 체크 String userPwd =*/
		 log.info("비밀번호 확인 요청 발생");
		 
		 String result = null;
		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		 
		 member.setPwd(pwd);
		 log.info("member 비밀번호 확인용 : " + member.getPwd());
		 log.info("form 에서 받아온 비밀번호 : " + pwd);
		 
		 if(encoder.matches(pwd, member.getPwd())) {
			 result ="pwdConfirmOK";
		 } else {
			 result = "pwdConfinmNO";
		 }
		 return result;
	 }
	 
	 /* 비밀번호 update */
	 /*
	 @PostMapping("/pwdUpdate") 
	 public String pwdUpdate(Member member,
	 RedirectAttributes rttr, HttpSession session) {
		 
		 log.info("비밀번호 변경 요청");
		 
		 mypageService.pwdUpdate(member);
		 
		 Member updateMember = new Member();
		 updateMember.setPwd(member.getPwd());
		 	 
	     return "redirect:/mypage/changinfo"; 
	 }
	*/

	/* 회원 비밀번호 변경 입력 폼 */
	@PostMapping("/changinfo/changinfoModify/changinfoPwdModify")
	public String changeInfoPwdModify(Member member, @AuthenticationPrincipal UserImpl user,
			HttpServletRequest request, RedirectAttributes rttr, Locale locale) {

		// 유저 정보 가져오기
		member.setUserNo(user.getUserNo());
		member.setId(user.getId());
		member.setPwd(user.getPwd());
		
		String pwd = request.getParameter("pwd");
		String newPwd = request.getParameter("newPwd");
		String userId = user.getId();
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		
//		log.info("비밀번호변경 member 정보 : " + member.toString());
//		//log.info("newPwd 정보 : " + newPwd.toString());
//		
//		log.info("pwd : " + pwd.toString());
//		log.info("newPwd : " + newPwd.toString());
//		log.info("userId : " + userId.toString());
		
		mypageService.pwdUpdate(userId, pwd, passwordEncoder.encode(newPwd));

		rttr.addFlashAttribute("successMessage", messageSource.getMessage("changeInfoPwdModify", null, locale));

		//user.setDetails(member);

		return "redirect:/mypage/changinfo";
	}

	/* 캠핑장 등록 페이지 */
	@GetMapping("/mypageCampEnrollment")
	public String mypageCampEnrollmentForm() {

		return "mypage/mypage_camp_enrollment";
	}

	/* 숙소 등록 */
	@GetMapping("/mypageCampEnrollmentRoom")
	public String mypage_camp_enrollment_roomForm(Camp camp, @AuthenticationPrincipal UserImpl user, Model model) {

		int userNo = user.getUserNo();
		int page = 1;
		
		// log.info("page : " + page);
		// log.info("user : " + user.toString());
		
		Map<String, Object> map = mypageService.selectMyCampList(userNo, page);

		model.addAttribute("campList", map.get("campList"));
		model.addAttribute("pi", map.get("pi"));
		model.addAttribute("campImageList", map.get("campImageList"));

		log.info("map : " + map.toString());
		log.info("model : " + model.toString());
		
		return "mypage/mypage_camp_enrollment_room";
	}

	/* 숙소 등록 form */
	@PostMapping("/mypageCampEnrollmentRoom")
	public String mypage_camp_enrollment_room(Member member, Camp camp, Room room, @AuthenticationPrincipal UserImpl user,
			@Value("${custom.path.upload-images}") String uploadFilesPath, Model model,
			@RequestParam MultipartFile[] roomMultiFiles, HttpServletRequest request, RedirectAttributes rttr,
			Locale locale, int campNo) {

		/* 
		 * @RequestParam(value="roomList[]") List<Room> roomList
		 * @RequestParam(value="CampBusinessTypeList") List<String> value
		 * 
		 * There is no getter for property named 'CampBusinessType' in 'class
		 * com.cp.campers.mypage.model.vo.Camp' 검색 결과 vo와 Mapper.xml 에서 parameterType타입
		 
		 * ? or property이름 불일치 해서 생기는 오류 인듯하다. CampBusinessType 를 처음에 getter 오류가 발생해서
		 * getter이 없어서 생기는 오류 인가 생각해서 List<Integer> businessType 에서
		 * List<CampBusinessType> campBusinessTypeList 변경 하였으나 같은 getter 오류가 발생. 반복문으로
		 * CampBusinessType 에 btype를 분리해서 db에 저장해서 for(String btype : btypeList ) {
		 * mypageMapper.insertCampBusinessType(btype); } 처리를 해줬는대 어떻게 수정해야 할지를 모르겠다...
		 *
		 * 추가
		 * mypageMapper.xml 에서 com.cp.campers.mypage.model.vo.Camp에 CampBusinessType가
		 * <collection property="CampBusinessType" resultMap="CampBusinessTypeResultMap"/>
		 * 여서 문제인걸까 ? column이 아니 라서 ?
		 * Pacage Explorer 에서 Camp에 vo 를 확인결과 CampBusinessType 가아니라
		 * CampBusinessTypeList로 확인됨 이걸 
		 */

		/* room에 Camp에 campNo를 가져와서 room을 insert 해야될거 같다...?
		 *  XXXX 이건 아닌듯 > 캠프에 유저정보 가져오기 camp.setUserNo(userNo);
		 * 
		 * room 에다가 campNo를 받아와서 room을 insert 해야됨. 사업자에 캠핑장을 여러개 등록할수 있으므로 등록할 캠핑장을 선택
		 * 해야됨. (userNo 에 campList를 가져와서 select로 전체 조회목록 만들기) 목록에 campNo를 name값으로 줘서
		 * room에 insert ? select ???
		 * 
		 * 로그인한 회원정보 user.getUserNo 로 캠프에 입력 캠프는 userNo 만 가지고 있음. 나머지 정보는 어떻게 가져올까 ?
		 * 
		 * 1. 사업자 회원 로그인 user.getUserNo
		 * 2. 사업자 회원 캠프장 찾기 room.setCampNo(camp.getCampNo)    XXX camp.getUserNo(user.setUserNo)
		 * 3. 캠프장에 숙소 추가하기 room.getCampNo(camp.setCampNo)
		 * 
		 */
		// room.setCampNo(camp.setCampNo());
		
		/* insertRoom에 campNo를 room에 넣어줌 */
				
		camp.setUserNo(user.getUserNo());
		//room.setCampNo(campNo);
		// camp.setCampNo(campNo);
		// room.setCampNo(camp.getCampNo());
		// room.setCampNo(camp.getCampNo(camp.setUserNo(user.getUserNo())));
		
		log.info("------------------------------------------------------------------");
		
		log.info("숙소 등록에 room : " + room.toString());
		
		/*
		 * List<String> values = value; for(int i = 0; i < value.size(); i++) {
		 * model.addAttribute("value", values); log.info(values.toString());
		 * log.info(value.toString()); }
		 */

		// room.setUserNo(camp.getCampNo());
		log.info("user : " + user.toString());
		log.info("camp : " + camp.toString());
		log.info("room : " + room.toString());

		// camp.setUserNo(user.getUserNo());

		// log.info("user : " + user.toString());
		// log.info("camp : " + camp.toString());

		/* 객실 사진을 저장할 경로 */
		String roomFilePath = uploadFilesPath + "/roomImg";

		/* 해당 파일 경로 존재 여부 확인하여 없을 경우 make directory */
		File mkdir3 = new File(roomFilePath);
		if (!mkdir3.exists())
			mkdir3.mkdirs();

		/* 객실 사진 파일에 관한 정보 보관 */
		List<Map<String, String>> files3 = new ArrayList<>();
		List<MultipartFile> roomMultiFileList = new ArrayList<>();

		Attachment atta2 = new Attachment();

		for (int i = 0; i < roomMultiFiles.length; i++) {
			if (roomMultiFiles[i].getSize() != 0) {
				roomMultiFileList.add(roomMultiFiles[i]);
			}
		}

		/* List<MultipartFile> 반복문 */
		for (int i = 0; i < roomMultiFileList.size(); i++) {
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
			for (int i = 0; i < roomMultiFileList.size(); i++) {
				Map<String, String> file3 = files3.get(i);
				roomMultiFileList.get(i).transferTo(new File(file3.get("roomFilePath") + "/" + file3.get("savedName")));

				atta2 = new Attachment();
				atta2.setFileName(file3.get("savedName3"));

				atta2.setFileOriginName(file3.get("originFileName3"));
				// atta2.setFileNewName(file.get("originFileNmae"));

				atta2.setFileRoute("/resources/images/uploadFiles/roomImg/");
				// atta2.setFileLevel(5);

				if (i == 0)
					atta2.setFileLevel(0);
				else
					atta2.setFileLevel(1);

				// mypageService.insertRoomImage(atta2);
			}

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			/* 실패 시 저장 된 파일 삭제 */
			for (int i = 0; i < roomMultiFileList.size(); i++) {
				Map<String, String> file3 = files3.get(i);
				new File(file3.get(roomFilePath) + "\\" + file3.get("savedName")).delete();
			}
		}

		// log.info("atta2 : " + atta2.toString());
		// log.info("room : " + room.toString());

		/* 숙소 등록 */
		//camp.setRoom(room)//
		// room.setCampNo(camp.getCampNo());
		
		/* 캠프, 캠프 타입, 시설 타입, 객실 등록한 로그 파일 받아오기 */
		// log.info("camp : " + camp.toString());

		mypageService.mypageCampEnrollmentRoom(room, atta2);

		/* 숙소 등록 메세지 */
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("insertCamp", null, locale));
		return "redirect:/mypage/mypageCampManagement";
	}

	
	
	
	
	
	
	/* 캠핑장 등록 입력폼 */
	/* @AuthenticationPrincipal UserImpl user 유저 정보 가져오기 */
	@PostMapping("/mypageCampEnrollment")

	public String mypageCampEnrollment(Camp camp, @AuthenticationPrincipal UserImpl user, @Value("${custom.path.upload-images}") String uploadFilesPath,
			Model model, @RequestParam MultipartFile singleFile, @RequestParam MultipartFile[] campMultiFiles,
			@RequestParam List<MultipartFile> roomMultiFiles, HttpServletRequest request, CampRecord campRecord,
			RedirectAttributes rttr, Locale locale) {

		
		// Camp = 사업장 
		// Room = 객실
		// singleFile = 사업자등록증
		// campMultiFiles = 사업장 사진들
		// roomMultiFiles = 객실 사진들
		// campRocord = 이력?
		// rttr , locale = 숙소등록 메세지 보내기 위함
		
		camp.setUserNo(user.getUserNo());

		/*-----------------------1. 캠핑장 먼저 insert 하는 로직 ----------------------------*/
		String filePath = uploadFilesPath + "businessImg";
		
		File mkdir = new File(filePath);
		
		if (!mkdir.exists())
			mkdir.mkdirs();
		
		String originFileName = singleFile.getOriginalFilename();
		String ext = originFileName.substring(originFileName.lastIndexOf("."));
		String savedName = UUID.randomUUID().toString().replace("-", "") + ext;
		
		try {
			singleFile.transferTo(new File(filePath + "\\" + savedName));
			camp.setCampPath("/resources/images/uploadFiles/businessImg/" + savedName);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		// 현재 campNo를 가져가서 이력만들기 위함
		campRecord.setCampNo(camp.getCampNo());

		// 캠핑장 타입, 부대시설 insert하기 위함(구현완료)
		String[] businessTypeCheck = request.getParameterValues("businessType");
		String[] facilityNoCheck = request.getParameterValues("facilityNo");
		
		List<String> btypeList = new ArrayList<>();
		for (String check : businessTypeCheck) {
			btypeList.add(check);
		}
		
		List<String> ftypeList = new ArrayList<>();
		for (String check2 : facilityNoCheck) {
			ftypeList.add(check2);
		}
		
		mypageService.mypageCampEnrollment(camp, btypeList, ftypeList);

		/* ---------------------------2. 사업장 사진 여러개 ------------------------------ */
		String campFilePath = uploadFilesPath + "/campImg";

		File mkdir2 = new File(campFilePath);
		if (!mkdir2.exists())
			mkdir2.mkdirs();

		List<Map<String, String>> files = new ArrayList<>();
		List<MultipartFile> campMultiFileList = new ArrayList<>();
		
		// 이미지가 실제로 존재하는지 처리해서 다시 List로 담음
		for (int i = 0; i < campMultiFiles.length; i++) {
			if (campMultiFiles[i].getSize() != 0) {
				campMultiFileList.add(campMultiFiles[i]);
			}
		}

		for (int i = 0; i < campMultiFileList.size(); i++) {
			String originFileName2 = campMultiFileList.get(i).getOriginalFilename();
			String ext2 = originFileName2.substring(originFileName2.lastIndexOf("."));
			String savedName2 = UUID.randomUUID().toString().replace("-", "") + ext2;

			Map<String, String> file = new HashMap<>();
			file.put("originFileName2", originFileName2);
			file.put("savedName2", savedName2);
			file.put("campFilePath", campFilePath);
			files.add(file);
		}

		try {
			for (int i = 0; i < campMultiFileList.size(); i++) {

				
				Map<String, String> file = files.get(i);
				campMultiFileList.get(i).transferTo(new File(file.get("campFilePath") + "\\" + file.get("savedName2")));
				Attachment attachment = new Attachment();
				attachment = new Attachment();
				attachment.setFileName(file.get("savedName2"));
				attachment.setFileOriginName(file.get("originFileName2"));
				attachment.setFileRoute("/resources/images/uploadFiles/campImg/");

				if (i == 0)
					attachment.setFileLevel(0);
				else
					attachment.setFileLevel(1);
				
				mypageService.campImageInsert(attachment);
				
			}

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			for (int i = 0; i < campMultiFileList.size(); i++) {
				Map<String, String> file = files.get(i);
				new File(file.get("campFilePath") + "\\" + file.get("savedName")).delete();
			}
		}

		/* ---------------------------5. 객실 먼저 insert ---------------------------- */
		
		String[] roomName = request.getParameterValues("roomName");
		String[] roomMember = request.getParameterValues("roomMember");
		String[] roomPrice = request.getParameterValues("roomPrice");
		String[] roomSize = request.getParameterValues("roomSize");
		String[] roomFloor = request.getParameterValues("roomFloor");
		String[] roomParking = request.getParameterValues("roomParking");
		String[] roomInfo = request.getParameterValues("roomInfo");
		String[] roomAmount = request.getParameterValues("roomAmount");
		String[] roomForm = request.getParameterValues("roomForm");
		
		List<String> roomNoList = new ArrayList<>();
		for(int i = 0 ; i < roomName.length; i++) {
			Room room = new Room();
			room.setRoomName(roomName[i]);
			room.setRoomMember(Integer.parseInt(roomMember[i]));
			room.setRoomPrice(Integer.parseInt(roomPrice[i]));
			room.setRoomSize(roomSize[i]);
			room.setRoomFloor(roomFloor[i]);
			room.setRoomParking(roomParking[i]);
			room.setRoomInfo(roomInfo[i]);
			room.setRoomAmount(Integer.parseInt(roomAmount[i]));
			room.setRoomForm(roomForm[i]);
			room.setCampNo(camp.getCampNo());
			log.info("room = "+room);
			roomNoList.add(mypageService.roomInsert(room));
		}
		
		
		/* ---------------------------4. 객실 사진 여러개 ------------------------------ */

		String roomFilePath = uploadFilesPath + "/roomImg";
 
		File mkdir3 = new File(roomFilePath);
		if (!mkdir3.exists())
			mkdir3.mkdirs();

		List<Map<String, String>> files3 = new ArrayList<>();


		for (int i = 0; i < roomMultiFiles.size(); i++) {
			
			Map<String, String> file3 = new HashMap<>();
			if(roomMultiFiles.get(i).getSize() > 0) {
			String originFileName3 = roomMultiFiles.get(i).getOriginalFilename();
			String ext3 = originFileName3.substring(originFileName3.lastIndexOf("."));
			String savedName3 = UUID.randomUUID().toString().replace("-", "") + ext3;

			
			file3.put("originFileName3", originFileName3);
			file3.put("savedName3", savedName3);
			file3.put("roomFilePath", roomFilePath);
			file3.put("thumbnail", i+"");
			file3.put("index", i+1+"");
			file3.put("isEmpty", "N");
			files3.add(file3);
			}else {
			file3.put("thumbnail", i+"");
			file3.put("index", i+1+"");
			file3.put("isEmpty", "Y");
			files3.add(file3);
			}
		}
		try {
			int index = Integer.parseInt(roomNoList.get(0));
			
			for (int i = 0; i < roomMultiFiles.size(); i++) {
				
				Map<String, String> file3 = files3.get(i);
				
				if(file3.get("isEmpty") == "N") {
					
				roomMultiFiles.get(i).transferTo(new File(file3.get("roomFilePath") + "\\" + file3.get("savedName3")));

				Attachment atta2 = new Attachment();
				atta2.setFileName(file3.get("savedName3"));
				atta2.setFileOriginName(file3.get("originFileName3"));
				atta2.setFileRoute("/resources/images/uploadFiles/roomImg/");

				if (Integer.parseInt(file3.get("thumbnail")) % 5 == 0) {
					atta2.setFileLevel(0);
				}else {
					atta2.setFileLevel(1);
				}
					
				atta2.setRoomNo(index);
			
				mypageService.roomImageInsert(atta2);
				
				}
				if(Integer.parseInt(file3.get("index")) % 5 == 0) {
					index++;
				}
				
				
			}

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			for (int i = 0; i < roomMultiFiles.size(); i++) {
				Map<String, String> file3 = files3.get(i);
				new File(file3.get(roomFilePath) + "\\" + file3.get("savedName")).delete();
			}
		}

		rttr.addFlashAttribute("successMessage", messageSource.getMessage("insertCamp", null, locale));
		return "redirect:/mypage";
		
	}

	/* 캠핑장 해지 */
	@GetMapping("/mypageCampManagementOut")
	public String mypageCampManagementOut() {
		return "mypage/mypage_camp_management_out";
	}

	/* 캠핑장 관리(사업자용) */
	@GetMapping("/mypageCampManagement")
	public String mypageCampManagement(Camp camp, @AuthenticationPrincipal UserImpl user, Model model) {
		
		int userNo = user.getUserNo();
		int page = 1;
		
		log.info("page : " + page);
		log.info("user : " + user.toString());
		
		Map<String, Object> map = mypageService.selectMyCampList(userNo, page);

		model.addAttribute("campList", map.get("campList"));
		model.addAttribute("pi", map.get("pi"));
		model.addAttribute("campImageList", map.get("campImageList"));

		log.info("map : " + map.toString());
		log.info("model : " + model.toString());
				
		return "mypage/mypage_camp_management";
	}

	/* 마이페이지 카테고리 */
	@GetMapping("/mypageCategory")
	public String mypageCategory() {
		return "mypage/mypage_category";
	}

	/* 회원 예약 내역 */
	@GetMapping("/mypageGuestReserve")
	public String mypageGuestReserve(@AuthenticationPrincipal UserImpl user, Model model) {
	
		int page = 1;
		int userNo = user.getUserNo();
		log.info("userNo : {}", userNo);

		Map<String, Object> map = mypageService.selectMyGuestReserveList(userNo, page);

		model.addAttribute("pi", map.get("pi"));
		model.addAttribute("reserveList", map.get("reserveList"));
		
		if (map.get("reserveList").toString().equals("[]")) {
			model.addAttribute("noResult", "아직 예약 내역이 없습니다.");
		}

		return "mypage/mypage_guest_reserve";
	}
	
	/* 회원 예약 내역 + 페이징*/
	@GetMapping("/mypageGuestReservePage")
	public String mypageGuestReservePage(@AuthenticationPrincipal UserImpl user, int page, Model model) {
	
		int userNo = user.getUserNo();
		log.info("userNo : {}", userNo);

		Map<String, Object> map = mypageService.selectMyGuestReserveList(userNo, page);

		model.addAttribute("pi", map.get("pi"));
		model.addAttribute("reserveList", map.get("reserveList"));
		
		if (map.get("reserveList").toString().equals("[]")) {
			model.addAttribute("noResult", "아직 예약 내역이 없습니다.");
		}

		return "mypage/mypage_guest_reserve";
	}
	
	@GetMapping("/reserveCancle")
	public String reserveCancle(int reserNo, RedirectAttributes rttr, Locale locale) {
		
		int result = mypageService.reserveCancle(reserNo);
		
		if(result > 0) {
			rttr.addFlashAttribute("successMessage", messageSource.getMessage("reserveCancle", null, locale));
		}
		
		return "redirect:/mypage/mypageGuestReserve";
	}

	/* 사업자 예약 내역 */
	@GetMapping("/mypageHostReserve")
	public String mypageHostReserve(int campNo, Camp camp, Model model, @AuthenticationPrincipal UserImpl user) {

		int userNo = user.getUserNo();
		int page = 1;
		
				
		log.info("user : " + user.toString());
		
		// log.info("camp : " + camp.toString());
		// log.info("model : " + model.toString());

		Map<String, Object> map = mypageService.selectMyHostReserveList(userNo, page);

		model.addAttribute("campList", map.get("campList"));
		model.addAttribute("pi", map.get("pi"));
		model.addAttribute("campImageList", map.get("campImageList"));
		model.addAttribute("reserveInfoList", map.get("reserveInfoList"));
		model.addAttribute("businessReservationList", map.get("businessReservationList"));

		
		
		// model.addAttribute("user", user.getUserNo());

		//log.info("map : " + map.toString());
		//log.info("model : " + model.toString());

		return"mypage/mypage_host_reserve";
	}

	/* 찜한 페이지 */
	@GetMapping("/wishcamp")
	public String wishCamp(int campNo, Camp camp, Model model, String dateIn, String dateOut, @AuthenticationPrincipal UserImpl user) {
		
		int userNo = user.getUserNo();
		int page = 1;
		
		Map<String, Object> map = mypageService.selectwishCampList(userNo, page);
		
		model.addAttribute("wishCampList", map.get("wishCampList"));
		model.addAttribute("pi", map.get("pi"));
		
		return "mypage/wishCamp";
	}

}
