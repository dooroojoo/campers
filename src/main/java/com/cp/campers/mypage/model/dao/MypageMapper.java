package com.cp.campers.mypage.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.member.model.vo.Member;
import com.cp.campers.mypage.model.vo.Camp;

@Mapper
public interface MypageMapper {

	Member findMemberById(String userId);
	
	void insertCamp(Camp camp);
	
}
