package com.cp.campers.mypage.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.admin.model.vo.PageInfo;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.mypage.model.vo.BusinessType;
import com.cp.campers.mypage.model.vo.Camp;

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
	
	// public Map<String, Object> findAllMember();
}
