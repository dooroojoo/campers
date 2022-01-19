package com.cp.campers.mypage.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.cp.campers.admin.model.dao.AdminMapper;
import com.cp.campers.board.model.vo.Attachment;
import com.cp.campers.board.model.vo.Board;
import com.cp.campers.camp.model.dao.CampMapper;
import com.cp.campers.camp.model.vo.Review;
import com.cp.campers.member.model.dao.MemberMapper;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.member.model.vo.UserImpl;
import com.cp.campers.mypage.model.dao.MypageMapper;
import com.cp.campers.mypage.model.vo.BusinessReservation;
import com.cp.campers.mypage.model.vo.BusinessType;
import com.cp.campers.mypage.model.vo.Camp;
import com.cp.campers.mypage.model.vo.CampBusinessType;
import com.cp.campers.mypage.model.vo.CampFacility;
import com.cp.campers.mypage.model.vo.ImageFile;
import com.cp.campers.mypage.model.vo.MypageCampManagement;
import com.cp.campers.mypage.model.vo.PageInfo;
import com.cp.campers.mypage.model.vo.Room;
import com.cp.campers.mypage.model.vo.WishCamp;
import com.cp.campers.reservePayment.model.vo.ReserveInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MypageServiceImpl implements MypageService{

	private final MypageMapper mypageMapper;
	private final MemberMapper memberMapper;
	private final AdminMapper adminMapper;
	private final CampMapper campMapper;
	
	@Autowired
	public MypageServiceImpl(MypageMapper mypageMapper,  MemberMapper memberMapper, AdminMapper adminMapper, CampMapper campMapper) {
		this.mypageMapper = mypageMapper;
		this.memberMapper = memberMapper;
		this.adminMapper = adminMapper;
		this.campMapper = campMapper;
	}

	/* 캠프장 등록 */
	@Override
	public void mypageCampEnrollment(Camp camp, List<String> btypeList,List<String> ftypeList) {
		
		mypageMapper.insertCamp(camp);
		
		for(String btype : btypeList ) {
			mypageMapper.insertCampBusinessType(btype);
		}
		for(String ftype : ftypeList ) {
			mypageMapper.insertCampFacility(ftype);
		}
		
		/* 신규업체 신청 시 이력테이블 */
		adminMapper.recordToNew(camp.getCampNo());
	
	}
	
	@Override
	public void campImageInsert(Attachment attachment) {
		mypageMapper.insertCampImage(attachment);
		mypageMapper.insertImageNo();
	}

	@Override
	public String roomInsert(Room room) {
		mypageMapper.insertRoom(room);
		return mypageMapper.selectRoomNoList();
		
	}

	@Override
	public void roomImageInsert(Attachment atta2) {
		mypageMapper.insertRoomImage(atta2);
		mypageMapper.insertImageNo2(atta2.getRoomNo());
	}

	/* 숙소 등록 */
	@Transactional
	@Override
	public void mypageCampEnrollmentRoom(Room room, Attachment atta2) {
				
				
			/* 숙소 등록 */
			mypageMapper.insertRoom2(room);
			/* 숙소 이미지 등록 */
			log.info("IMPL에서 room : " + room.toString());
			mypageMapper.insertRoomImage(atta2);
			mypageMapper.insertImageNo3(atta2.getRoomNo());
			log.info("IMPL에서 atta2 : " + atta2.toString());
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
		//mypageMapper.insertImageNo2();	
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
	@Override
	public int changeInfoMemberout(Member member) {
		String pwd = mypageMapper.selectPwd(member);
		
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(encoder.matches(member.getPwd(), pwd)) {
			return mypageMapper.changeInfoMemberout(member);
		}
		
		return 0;
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
        
        //log.info(listCount+"");
		
		PageInfo pi = new PageInfo(page,listCount, 1, 3);
		pi.setStartRow(page, pi.getBoardLimit());
		pi.setEndRow(pi.getStartRow(), pi.getBoardLimit());

		Map<String, Object> param = new HashMap<>();
		param.put("pi", pi);
		param.put("writer", writer);
		
		List<Board> boardList = mypageMapper.selectMyBoardList(param);
		
		//log.info("boardList : " + boardList.toString());
		
		List<Board> thumbnailList = mypageMapper.selectThumbnailList();
		
		//log.info("thumbnailList : " + thumbnailList.toString());
		
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
		
		log.info("memberList : " + memberList.toString());
		
		List<Camp> thumbnailList = mypageMapper.selectThumbnailList2();
		
		log.info("thumbnailList : " + thumbnailList.toString());
		
		Map<String, Object> map = new HashMap<>();
		map.put("memberList", memberList);
		map.put("pi", pi);
		map.put("thumbnailList", thumbnailList);
		
		return map;
	}
	
	@Transactional
	@Override
	public Map<String, Object> selectMyWishCampList(int userNo, int page) {
		
		int listCount = mypageMapper.getListCountMyWishCamp(userNo);
		
		log.info(listCount+"");
		
		PageInfo pi = new PageInfo(page, listCount, 1, 5);
		pi.setStartRow(page, pi.getBoardLimit());
		pi.setEndRow(pi.getStartRow(), pi.getBoardLimit());
		
		Map<String, Object> param = new HashMap<>();
		param.put("pi", pi);
		param.put("userNo", userNo);
				
		List<WishCamp> wishCampList = mypageMapper.selectWishCampList(userNo);
		
		log.info("wishCampList : " + wishCampList.toString());
		
		Map<String, Object> map = new HashMap<>();
		map.put("wishCampList", wishCampList);
		map.put("pi", pi);		
		
		return map;
		
	}
	
	/* 내 캠프장 리스트 */
	@Transactional
	@Override
	public Map<String, Object> selectMyCampList(int userNo, int page) {
		// int listCount = mypageMapper.getListCountMyHostReserveList(userNo);
		/* 등록한 캠프장 수 */
		// log.info("listCount : " + listCount+"");
		
		PageInfo pi = new PageInfo(page, 10, 10, 5);
		pi.setStartRow(page, pi.getBoardLimit());
		pi.setEndRow(pi.getStartRow(), pi.getBoardLimit());
				
		Map<String, Object> param = new HashMap<>();
		param.put("pi", pi);
		param.put("userNo", userNo);
		//param.put("cbt", cbt);
		
		log.info("param : " + param.toString());		
		
		List<MypageCampManagement> mypageCampManagementList = mypageMapper.selectmypageCampManagementList(param);
		
		List<Camp> campList = mypageMapper.selectMyCampList(param);
		
		List<Camp> campImageList = mypageMapper.selectCampImageList();	
		
		
		log.info("campList : " + campList.toString());
		log.info("campImageList : " + campImageList.toString());
		
		Map<String, Object> map = new HashMap<>();
		map.put("pi", pi);
		map.put("campList", campList);
		map.put("campImageList", campImageList);
		map.put("mypageCampManagementList", mypageCampManagementList);
		
		return map;
	}
	
	/* 사업자 예약내역 확인  */
	@Transactional
	@Override
	public Map<String, Object> selectMyHostReserveList(int userNo, int page) {
		// int listCount = mypageMapper.getListCountMyHostReserveList(userNo);
		
		// log.info("listCount : " + listCount+"");
		
		PageInfo pi = new PageInfo(page, 10, 10, 5);
		pi.setStartRow(page, pi.getBoardLimit());
		pi.setEndRow(pi.getStartRow(), pi.getBoardLimit());
		
		Map<String, Object> param = new HashMap<>();
		param.put("pi", pi);
		param.put("userNo", userNo);
		 
		// log.info("param : " + param.toString());
		
		List<Camp> campList = mypageMapper.selectMyHostReserveList(param);
		
		List<Camp> campImageList = mypageMapper.selectCampImageList();
		
		//List<ReserveInfo> reserveInfoList = mypageMapper.selectReserveInfoList();
		
		List<BusinessReservation> businessReservationList = mypageMapper.selectBusinessReservationList(userNo);
		
		//List<ReserveInfo> reserveList = mypageMapper.selectHostReserveList(param);
						
		//log.info("campList : " + campList.toString());		
		//log.info("reserveInfoList : " + reserveInfoList.toString());
		log.info("businessReservationList : " + businessReservationList.toString());
		//log.info("reserveList : " + reserveList.toString());
				
		Map<String, Object> map = new HashMap<>();
		map.put("pi", pi);
		map.put("campList", campList);
		map.put("campImageList", campImageList);
		//map.put("reserveInfoList", reserveInfoList);
		map.put("businessReservationList", businessReservationList);
		
		return map;
	}


	/* 회원 예약내역 찾기 */
	@Override
	public Map<String, Object> selectMyGuestReserveList(int userNo, int page) {
		
		// 회원 총 예약 개수
		int listCount = mypageMapper.getCountMyGuestReserveList(userNo);
		
		log.info("listCount : {}", listCount);
		
		// page 객체
		PageInfo pi = new PageInfo(page, listCount, 10, 5);
		pi.setStartRow(page, pi.getBoardLimit());
		pi.setEndRow(pi.getStartRow(), pi.getBoardLimit());
		
		Map<String, Object> param = new HashMap<>();
		param.put("pi", pi);
		param.put("userNo", userNo);
		
		// 예약리스트
		List<ReserveInfo> reserveList = mypageMapper.selectMyGuestReserveList(param);
		
		Map<String, Object> map = new HashMap<>();
		map.put("pi", pi);
		map.put("reserveList", reserveList);
		
		log.info("reserveList : {}", reserveList);
		
		return map;
	}
	
	@Override
	@Transactional
	public int reserveCancle(int reserNo) {
		return mypageMapper.reserveCancle(reserNo);
	}
	
	/* 프로필 사진 변경 */
	@Transactional
	@Override
	public Member updateProfilePath(Member member) {

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
		Map<String,String> param = new HashMap<>();
		param.put("userId", userId);
		param.put("pwd", pwd);
		param.put("newPwd", newPwd);
		log.info("param"+param);
		mypageMapper.pwdUpdate(param);		
	}

	@Override
	public Map<String, Object> selectwishCampList(int userNo, int page) {
				
		PageInfo pi = new PageInfo(page, 10, 10, 8);
		pi.setStartRow(page, pi.getBoardLimit());
		pi.setEndRow(pi.getStartRow(), pi.getBoardLimit());
		
		
		
		Map<String, Object> param = new HashMap<>();
		param.put("pi", pi);
		param.put("userNo", userNo);				
		
		List<Camp> campList = mypageMapper.selectMyHostReserveList(param);
		// List<Camp> campImageList = mypageMapper.selectCampImageList();				
		List<WishCamp> wishCampList = mypageMapper.selectWishCampList(userNo);
		
		
		log.info("wishCampList : " + wishCampList.toString());
						
		Map<String, Object> map = new HashMap<>();
		map.put("pi", pi);
		/*
		map.put("camp", camp2);
		map.put("roomList", roomList);
		map.put("reviewList", reviewList);
		map.put("campList", campList);
		*/
		
		// map.put("campImageList", campImageList);
		map.put("wishCampList", wishCampList);
				
		return map;
	}

	

	
}
