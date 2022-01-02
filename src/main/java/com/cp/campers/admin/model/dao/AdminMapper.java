package com.cp.campers.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.member.model.vo.Member;
import com.cp.campers.member.model.vo.MemberRole;

@Mapper
public interface AdminMapper {

	List<Member> findAllMember();

	int updateMember(Member member);

	int updateMemberRole(MemberRole mr);
}
