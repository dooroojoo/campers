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
	public List<Member> selectMember(Member member){
		return mypageMapper.selectMember(member);
	}

	@Override
	public Map<String, Object> findAllMember(int page) {
		
		// 1. 총 회원수
		int listCount = mypageMapper.getListCount();
		
		PageInfo pi = new PageInfo(page, listCount, 10, 10);
		pi.setStartRow(page);
		pi.setEndRow(pi.getStartRow());
		List<Member> memberList = mypageMapper.findAllMember(pi);
		
		Map<String, Object> map = new HashMap<>();
		map.put("pi", pi);
		map.put("memberList", memberList);
				
		return map;
	}

	@Override
	public List<Member> findMember() {
		
		return null;
	}
	
}
