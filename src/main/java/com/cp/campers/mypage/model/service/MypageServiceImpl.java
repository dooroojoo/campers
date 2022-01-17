package com.cp.campers.mypage.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.cp.campers.admin.model.dao.AdminMapper;
import com.cp.campers.board.model.vo.Attachment;
import com.cp.campers.board.model.vo.Board;
import com.cp.campers.member.model.dao.MemberMapper;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.member.model.vo.UserImpl;
import com.cp.campers.mypage.model.dao.MypageMapper;
import com.cp.campers.mypage.model.vo.BusinessType;
import com.cp.campers.mypage.model.vo.Camp;
import com.cp.campers.mypage.model.vo.CampBusinessType;
import com.cp.campers.mypage.model.vo.CampFacility;
import com.cp.campers.mypage.model.vo.ImageFile;
import com.cp.campers.mypage.model.vo.PageInfo;
import com.cp.campers.mypage.model.vo.Room;
import com.cp.campers.reservePayment.model.vo.ReserveInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MypageServiceImpl implements MypageService{

	private final MypageMapper mypageMapper;
	private final MemberMapper memberMapper;
	private final AdminMapper adminMapper;
	
	@Autowired
	public MypageServiceImpl(MypageMapper mypageMapper,  MemberMapper memberMapper, AdminMapper adminMapper) {
		this.mypageMapper = mypageMapper;
		this.memberMapper = memberMapper;
		this.adminMapper = adminMapper;
	}

	/* 캠프장 등록 */
	@Transactional
	@Override
	public void mypageCampEnrollment(Camp camp, List<String> btypeList,
			List<String> ftypeList, Attachment attachment, Attachment atta2) {
		
		/* CAMP TABLE INSERT */
		mypageMapper.insertCamp(camp);
		
		/* CAMP_BUSINESS_TYPE INSERT */
		/* 반복문으로 Integer 선택한 체크박스 businessNo 를 캠프 비즈니스 타입으로
		 * mypageMapper에 CampBusinessType 에 businessNo 입력 */
		//for(Integer businessNo : camp.getBusinessType()) {
	    //	mypageMapper.insertCampBusinessType(businessNo);			
		//}
		//log.info("camp : " + camp.toString());
		
		for(String btype : btypeList ) {
			mypageMapper.insertCampBusinessType(btype);
		}
		
		/* CAMP_FACILITY INSERT */
		//for(Integer facilityNo : camp.getFacilityNo()) {
		//	mypageMapper.insertCampFacility(facilityNo);
		//}
		
		for(String ftype : ftypeList ) {
			mypageMapper.insertCampFacility(ftype);
		}
		
		//mypageMapper.insertCampFacility(camp.getCampNo());
		
		/* ROOM INSERT */
		//for(Room room : camp.getRoomList()) {
		//	mypageMapper.insertRoom(room);
		// }			
		mypageMapper.insertRoom(camp.getRoom());	
		
		mypageMapper.insertCampImage(attachment);
		mypageMapper.insertImageNo();
		
		mypageMapper.insertRoomImage(atta2);
		mypageMapper.insertImageNo2();
				
		
		/* CAMP_FILE INSERT */
		//mypageMapper.insertCampFile(camp.getCampFile());
		//for(Integer fileNo : camp.getCampFile()) {
		//	mypageMapper.insertCampFile(fileNo);
		//}
		
		/* ROOM_FILE INSERT */
		//mypageMapper.insertRoomFile(camp.getRoomFile());
		
		/* 신규업체 신청 시 이력테이블 */
		adminMapper.recordToNew(camp.getCampNo());
	
	}	

	/* 숙소 등록 */
	@Transactional
	@Override
	public void mypageCampEnrollmentRoom(List<Room> roomList, Attachment atta2) {
				
		/*
			생각해보니 room에다가 insert 해야될듯...
			mypageMapper.insertCamp(room);
			
			Optional int parameter 'campNo' is present but cannot be translated into a null
			value due to being declared as a primitive type. Consider declaring it as object 
			wrapper for the corresponding primitive type.
			
		*/		
		for(Room room : roomList) {
			/* 숙소 등록 */
			mypageMapper.insertRoom(room);
			/* 숙소 이미지 등록 */
			mypageMapper.insertRoomImage(atta2);
			mypageMapper.insertImageNo2();
		}
	}

	/* 캠핑장 사진 등록*/
	@Transactional
	@Override
	public void insertCampImage(Attachment attachment) {
		mypageMapper.insertCampImage(attachment);
		mypageMapper.insertImageNo();		
	}

	/* 객실 사진 등록 */
	@Transactional
	@Override
	public void insertRoomImage(Attachment atta2) {
		mypageMapper.insertRoomImage(atta2);
		mypageMapper.insertImageNo2();	
	}

	
	@Override
	public List<Member> findAllMember(){
		return mypageMapper.findAllMember();
	}
	
	
	@Override
	public List<Board> findAllBoard() {
		return mypageMapper.findAllBoard();
	}
	
	/* 회원정보 수정 */
	@Transactional
	@Override
	public Member changeInfoModify(Member member) {
		
		mypageMapper.changeInfoModify(member);
				
		return memberMapper.findMemberById(member.getId());
	}

	/* 비밀번호 변경 */
	@Transactional
	@Override
	public void changeInfoPwdModify(Member member) {
		
		int newPwd = mypageMapper.changeInfoPwdModify(member);
		
		member.setPwd(member.getPwd());
		
		mypageMapper.changeInfoPwdModify(member);
		
	}

	/* 닉네임 체크 */
	@Transactional
	@Override
	public int nickNameCheck(String nickName) {
		int cnt = mypageMapper.nickNameCheck(nickName);
		return cnt;
	}

	/* 회원 탈퇴 */
	@Transactional
	@Override
	public Member changeInfoMemberout(Member member) {
	
		mypageMapper.changeInfoMemberout(member);
		
		return member;
	}


	@Override
	public int selectCampNo() {
		return mypageMapper.selectCampNo();
	}

	@Transactional
	@Override
	public Map<String, Object> selectMyPageList(int writer) {
		int listCount = mypageMapper.getListCountMyMember(writer);
        
        log.info(listCount+"");
		
		Map<String, Object> param = new HashMap<>();
		param.put("writer", writer);
		
		List<Member> memberList = mypageMapper.selectMyMemberList(param);
		
		List<Member> thumbnailList = memberMapper.selectThumbnailList();
		
		Map<String, Object> map = new HashMap<>();
		map.put("memberList", memberList);
		map.put("thumbnailList", thumbnailList);
		
		return map;
	}

	@Transactional
	@Override
	public Map<String, Object> selectMyBoardList(int writer, int page) {
		int listCount = mypageMapper.getListCountMyBoard(writer);
        
        log.info(listCount+"");
		
		PageInfo pi = new PageInfo(page,listCount, 1, 3);
		pi.setStartRow(page, pi.getBoardLimit());
		pi.setEndRow(pi.getStartRow(), pi.getBoardLimit());

		Map<String, Object> param = new HashMap<>();
		param.put("pi", pi);
		param.put("writer", writer);
		
		List<Board> boardList = mypageMapper.selectMyBoardList(param);
		
		List<Board> thumbnailList = mypageMapper.selectThumbnailList();
		
		Map<String, Object> map = new HashMap<>();
		map.put("pi", pi);
		map.put("boardList", boardList);
		map.put("thumbnailList", thumbnailList);
		
		return map;
	}


	/* 내 정보 찾기 */
	@Transactional
	@Override
	public Map<String, Object> selectMyMemberList(int userNo, int page) {
		
		int listCount = mypageMapper.getListCountMyMember(userNo);
		
		log.info(listCount+"");
		
		PageInfo pi = new PageInfo(page, listCount, 1, 1);
		pi.setStartRow(page, pi.getBoardLimit());
		pi.setEndRow(pi.getStartRow(), pi.getBoardLimit());
		
		Map<String, Object> param = new HashMap<>();
		param.put("pi", pi);
		param.put("userNo", userNo);
		
		List<Member> memberList = mypageMapper.selectMyMemberList(param);
		
		List<Member> thumbnailList = mypageMapper.selectThumbnailList2();
		
		Map<String, Object> map = new HashMap<>();
		map.put("memberList", memberList);
		map.put("pi", pi);
		map.put("thumbnailList", thumbnailList);
		
		return map;
	}
	
	/* 내 캠프장 리스트 */
	@Transactional
	@Override
	public Map<String, Object> selectMyCampList(int userNo, int page) {
		int listCount = mypageMapper.getListCountMyHostReserveList(userNo);
		/* 등록한 캠프장 수 */
		log.info("listCount : " + listCount+"");
		
		PageInfo pi = new PageInfo(page, listCount, 10, 5);
		pi.setStartRow(page, pi.getBoardLimit());
		pi.setEndRow(pi.getStartRow(), pi.getBoardLimit());
				
		Map<String, Object> param = new HashMap<>();
		param.put("pi", pi);
		param.put("userNo", userNo);
		//param.put("cbt", cbt);
		
		log.info("param : " + param.toString());		
		
		List<Camp> campList = mypageMapper.selectMyCampList(param);
		
		List<ImageFile> campImageList = mypageMapper.selectCampImageList();
		
		
		log.info("campList : " + campList.toString());
		log.info("campImageList : " + campImageList.toString());
		
		Map<String, Object> map = new HashMap<>();
		map.put("pi", pi);
		map.put("campList", campList);
		map.put("campImageList", campImageList);
		
		return map;
	}
	
	/* 사업자 예약내역 확인  */
	@Transactional
	@Override
	public Map<String, Object> selectMyHostReserveList(int userNo, int page) {
		int listCount = mypageMapper.getListCountMyHostReserveList(userNo);
		
		log.info("listCount : " + listCount+"");
		
		PageInfo pi = new PageInfo(page, listCount, 10, 5);
		pi.setStartRow(page, pi.getBoardLimit());
		pi.setEndRow(pi.getStartRow(), pi.getBoardLimit());
		
		Map<String, Object> param = new HashMap<>();
		param.put("pi", pi);
		param.put("userNo", userNo);
		
		log.info("param : " + param.toString());
		
		List<Camp> campList = mypageMapper.selectMyHostReserveList(param);
		
		List<ImageFile> campImageList = mypageMapper.selectCampImageList();
		
		List<ReserveInfo> reserveList = mypageMapper.selectHostReserveList(param);
				
		log.info("campList : " + campList.toString());
		log.info("campImageList : " + campImageList.toString());
		log.info("reserveList : " + reserveList.toString());
				
		Map<String, Object> map = new HashMap<>();
		map.put("pi", pi);
		map.put("campList", campList);
		map.put("campImageList", campImageList);
		map.put("reserveList", reserveList);
		
		return map;
	}


	/* 회원 예약내역 찾기 */
	@Transactional
	@Override
	public Map<String, Object> selectMyGuestReserveList(int userNo, int page) {
		int listCount = mypageMapper.getListCountMyHostReserveList(userNo);
		
		log.info("listCount : " + listCount+"");
		
		PageInfo pi = new PageInfo(page, listCount, 10, 5);
		pi.setStartRow(page, pi.getBoardLimit());
		pi.setEndRow(pi.getStartRow(), pi.getBoardLimit());
		
		Map<String, Object> param = new HashMap<>();
		param.put("pi", pi);
		param.put("userNo", userNo);
		
		log.info("param : " + param.toString());
		
		List<Camp> campList = mypageMapper.selectMyGuestReserveList(param);
		
		List<ImageFile> campImageList = mypageMapper.selectCampImageList();
		
		List<ReserveInfo> reserveList = mypageMapper.selectHostReserveList(param);
				
		log.info("campList : " + campList.toString());
		log.info("campImageList : " + campImageList.toString());
		log.info("reserveList : " + reserveList.toString());
				
		Map<String, Object> map = new HashMap<>();
		map.put("pi", pi);
		map.put("campList", campList);
		map.put("campImageList", campImageList);
		map.put("reserveList", reserveList);
		
		return map;
	}

	/* 프로필 사진 변경 */
	@Transactional
	@Override
	public Member updateProfilePath(Member member) {
		/*
		member.setUserNo(user.getUserNo());
		
		
		mypageMapper.updateProfilePath(member);
		
		return updateProfilePath(member, user);
		//return memberMapper.findMemberById(member.getId());
		*/
		log.info("impl member : " + member.toString());
		
		mypageMapper.updateProfilePath(member);		
		
		return memberMapper.findMemberById(member.getId());		
	}

	@Override
	public void deleteMember(Member member) {
		
		mypageMapper.deleteMember(member);
	}

	@Override
	public void pwdUpdate(String userId, String pwd, String newPwd) {
		mypageMapper.pwdUpdate(userId, pwd, newPwd);		
	}
	
	/*
	@Override
	public String pwdCheck(String id) {
		HttpSession sqlsession = new HttpSession();
		sqlsession.selectOne("mypageMapper.pwdCheck", id);
		return mypageMapper.pwdCheck(id);
	}

	@Override
	public void pwdUpdate(String id, String hashedPwd) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pwd", hashedPwd);
		
		id.get
		
		mypageMapper.pwdUpdate(id, hashedPwd);
		return;
	}
	 */
		
	
}
