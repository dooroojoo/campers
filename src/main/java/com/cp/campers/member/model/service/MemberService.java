package com.cp.campers.member.model.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cp.campers.member.model.vo.Member;

public interface MemberService extends UserDetailsService{

	void signUp(Member member);

	String selectUserId(Member member);
	
	String sendEmail(Member vo, String div) throws Exception;

	String findPwd(Member member) throws Exception;

	Member findUserByUserId(String id);

	int idCheck(String id);

	Member loginFailure(String userId);

	void loginFailCount(String username);

	int checkFailCount(String username);

	void disabledMember(String username);

}
