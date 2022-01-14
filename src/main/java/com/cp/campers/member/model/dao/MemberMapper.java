package com.cp.campers.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.board.model.vo.Board;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.member.model.vo.MemberRole;

@Mapper
public interface MemberMapper {

	Member findMemberById(String userId);
	
	void insertMember(Member member);
	
	void insertMemberRole(MemberRole memberRole);

	String selectUserId(Member member);

	void modifyPw(Member user);

	Member readUser(Member member);

	int idCheck(String id);

	List<Member> selectThumbnailList();