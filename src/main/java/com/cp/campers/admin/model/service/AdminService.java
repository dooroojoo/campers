package com.cp.campers.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cp.campers.member.model.vo.Member;

@Service
public interface AdminService {

	List<Member> findAllMember();
	
	int updateMember(Member member, int authorityCode);
}
