package com.cp.campers.member.model.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cp.campers.member.model.vo.Member;

public interface MemberService extends UserDetailsService{

	void signUp(Member member);
}
