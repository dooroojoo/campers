package com.cp.campers.mypage.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.admin.model.vo.PageInfo;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.mypage.model.vo.BusinessType;
import com.cp.campers.mypage.model.vo.Camp;
import com.cp.campers.mypage.model.vo.CampBusinessType;
import com.cp.campers.mypage.model.vo.CampFacility;
import com.cp.campers.mypage.model.vo.Room;

@Mapper
public interface MypageMapper {
	
	public List<Member> selectMember(Member member);
	
	/* 캠핑장 등록 */
	void insertCamp(Camp camp);

	/* 캠핑장 타입 등록 */
	void insertBusinessType(BusinessType businessType);

	// 회원수
	int getListCount();
	
	// 회원목록
	List<Member> findAllMember(PageInfo pi);

	// 사업장 타입 등록
	// Integer 체크박스 담기
	void insertCampBusinessType(Integer businessNo);

	// 사업장 시설 등록
	// Integer 체크박스 담기
	void insertCampFacility(Integer facilityNo);

	// 객실 등록
	void insertRoom(Room room);
	
	// public Map<String, Object> findAllMember();
}
