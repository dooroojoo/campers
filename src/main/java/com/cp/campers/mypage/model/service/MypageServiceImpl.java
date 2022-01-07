package com.cp.campers.mypage.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp.campers.admin.model.vo.PageInfo;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.mypage.model.dao.MypageMapper;
import com.cp.campers.mypage.model.vo.Camp;
import com.cp.campers.mypage.model.vo.CampBusinessType;
import com.cp.campers.mypage.model.vo.CampFacility;
import com.cp.campers.mypage.model.vo.Room;

@Service
public class MypageServiceImpl implements MypageService{

	private final MypageMapper mypageMapper;
	
	@Autowired
	public MypageServiceImpl(MypageMapper mypageMapper) {
		this.mypageMapper = mypageMapper;
	}

	/* 캠프장 등록 */
	@Transactional
	@Override
	public void mypageCampEnrollment(Camp camp) {
		
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
	}
	
	@Override
	public List<Member> findAllMember(){
		return mypageMapper.findAllMember();
	}

	/* 회원정보 수정 */
	@Transactional
	@Override
	public void changeInfoModify(Member member/*, String email, 
			String phone, String nickName*/) {
		/*
		member.setEmail(member.getEmail());
		member.setPhone(member.getPhone());
		member.setNickName(member.getNickName());
		*/
		mypageMapper.changeInfoModify(member);
	}

	/* 비밀번호 변경 */
	@Transactional
	@Override
	public void changeInfoPwdModify(Member member, String pwd) {
		
		member.setPwd(member.getPwd());
		
		mypageMapper.changeInfoPwdModify(member);		
	}

	/* 닉네임 체크 */
	@Override
	public int nickNameCheck(String nickName) {
		int cnt = mypageMapper.nickNameCheck(nickName);
		return cnt;
	}

	/*
	 * @Override public Map<String, Object> findAllMember() {
	 * 
	 * // 1. 총 회원수 int listCount = mypageMapper.getListCount();
	 * 
	 * List<Member> memberList = mypageMapper.findAllMember();
	 * 
	 * Map<String, Object> map = new HashMap<>();
	 * 
	 * map.put("memberList", memberList);
	 * 
	 * return map; }
	 */
	
	
}
