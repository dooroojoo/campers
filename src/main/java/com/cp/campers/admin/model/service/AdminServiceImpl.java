package com.cp.campers.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp.campers.admin.model.dao.AdminMapper;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.member.model.vo.MemberRole;

@Service("adminService")
public class AdminServiceImpl implements AdminService{

	private final AdminMapper adminMapper;
	
	@Autowired
	public AdminServiceImpl(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}
	
	@Override
	public List<Member> findAllMember() {
		return adminMapper.findAllMember();
	}

	@Transactional
	@Override
	public int updateMember(Member member, int authorityCode) {
		int result = 0;
		int result1 = adminMapper.updateMember(member);
		
		MemberRole mr = new MemberRole();
		mr.setMemberNo(member.getUserNo());
		mr.setAuthorityCode(authorityCode);
		
		int result2 = adminMapper.updateMemberRole(mr);
		
		if(result1==1 && result2==1) result = 1;
		
		return result;
	}

}
