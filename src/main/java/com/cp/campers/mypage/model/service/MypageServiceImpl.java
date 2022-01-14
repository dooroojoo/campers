package com.cp.campers.mypage.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp.campers.admin.model.dao.AdminMapper;
import com.cp.campers.board.model.vo.Attachment;
import com.cp.campers.board.model.vo.Board;
import com.cp.campers.member.model.dao.MemberMapper;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.mypage.model.dao.MypageMapper;
import com.cp.campers.mypage.model.vo.Camp;
import com.cp.campers.mypage.model.vo.PageInfo;

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
	public void mypageCampEnrollment(Camp camp,Attachment attachment,Attachment atta2) {
		
		/* CAMP TABLE INSERT */
		mypageMapper.insertCamp(camp);
		
		/* CAMP_BUSINESS_TYPE INSERT */
		/* 반복문으로 Integer 선택한 체크박스 businessNo 를 캠프 비즈니스 타입으로
		 * mypageMapper에 CampBusinessType 에 businessNo 입력 */
		for(Integer businessNo : camp.getBusinessType()) {
			mypageMapper.insertCampBusinessType(businessNo);			
		}
		
		/* CAMP_FACILITY INSERT */
		for(Integer facilityNo : camp.getFacilityNo()) {
			mypageMapper.insertCampFacility(facilityNo);
		}
		
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
	public Member changeInfoModify(Member member/*, String email, 
			String phone, String nickName*/) {
		
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
	@Override
	public int nickNameCheck(String nickName) {
		int cnt = mypageMapper.nickNameCheck(nickName);
		return cnt;
	}

	/* 회원 탈퇴 */
	@Override
	public void changeInfoMemberout(Member member) {
	
		mypageMapper.changeInfoMemberout(member);		
	}


	/* 숙소 등록 */
	@Transactional
	@Override
	public void mypage_camp_enrollment_room(Camp camp) {
		mypageMapper.insertRoom(camp.getRoom());
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
	public int selectCampNo() {
		return mypageMapper.selectCampNo();
	}

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


	/* 객실 사진 등록 */
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

	/* 사업자 예약내역 확인 */
	@Override
	public Map<String, Object> selectMyHostReserveList(int writer, int page) {
		int listCount = mypageMapper.getListCountMyHostReserveList(writer);
		
		PageInfo pi = new PageInfo(page, listCount, 10, 7);
		pi.setStartRow(page, pi.getBoardLimit());
		pi.setEndRow(pi.getStartRow(), pi.getBoardLimit());
		
		Map<String, Object> param = new HashMap<>();
		param.put("pi", pi);
		param.put("writer", writer);
		
		List<Camp> campList = mypageMapper.selectMyHostReserveList(param);
		
		Map<String, Object> map = new HashMap<>();
		map.put("pi", pi);
		map.put("campList", campList);
		
		return map;
	}

	
	
	
}
