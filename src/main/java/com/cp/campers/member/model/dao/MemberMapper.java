package com.cp.campers.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.member.model.vo.Member;
import com.cp.campers.member.model.vo.MemberRole;

@Mapper
public interface MemberMapper {

	Member findMemberById(String userId);
	
	void insertMember(Member member);
	
	void insertMemberRole(MemberRole memberRole);
}
