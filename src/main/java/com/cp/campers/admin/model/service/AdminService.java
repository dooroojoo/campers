package com.cp.campers.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cp.campers.admin.model.vo.Search;
import com.cp.campers.member.model.vo.Member;

@Service
public interface AdminService {

	Map<String, Object> findAllMember(int page);
	
	int updateMember(Member member, int authorityCode);

	Map<String, Object> searchMember(int page, Search search);
}
