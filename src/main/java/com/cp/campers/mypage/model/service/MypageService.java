package com.cp.campers.mypage.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cp.campers.member.model.vo.Member;
import com.cp.campers.mypage.model.vo.Camp;

public interface MypageService{

	void mypageCampEnrollment(Camp camp);

	List<Member> selectMember(Member member);

	// 회원목록
	Map<String, Object> findAllMember(int page);

	List<Member> findMember();
}
