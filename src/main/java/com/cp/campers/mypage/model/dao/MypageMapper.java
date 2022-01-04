package com.cp.campers.mypage.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.member.model.vo.Member;
import com.cp.campers.mypage.model.vo.BusinessType;
import com.cp.campers.mypage.model.vo.Camp;

@Mapper
public interface MypageMapper {
	
	/* 캠핑장 등록 */
	void insertCamp(Camp camp);

	/* 캠핑장 타입 등록 */
	void insertBusinessType(BusinessType businessType);
}
