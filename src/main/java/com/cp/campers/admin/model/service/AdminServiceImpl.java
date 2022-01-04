package com.cp.campers.admin.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp.campers.admin.model.dao.AdminMapper;
import com.cp.campers.admin.model.vo.PageInfo;
import com.cp.campers.admin.model.vo.Search;
import com.cp.campers.camp.model.vo.Camp;
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
	public Map<String, Object> findAllMember(int page) {
		
		// 1. 총 회원수
		int listCount = adminMapper.getListCount();
		// 2. PageInfo 객체
		PageInfo pi = new PageInfo(page, listCount, 10, 10);
		// 3. 페이징 처리된 회원목록
		pi.setStartRow(page);
		pi.setEndRow(pi.getStartRow());
		List<Member> memberList = adminMapper.findAllMember(pi);
		
		Map<String, Object> map = new HashMap<>();
		map.put("pi", pi);
		map.put("memberList", memberList);
		
		return map;
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

	@Override
	public Map<String, Object> searchMember(int page, Search search) {
		
		// 1. 검색 회원수
		int listCount = adminMapper.getListCountBySearch(search);
		// 2. PageInfo 객체
		PageInfo pi = new PageInfo(page, listCount, 10, 10);
		// 3. 페이징 처리된 회원목록
		pi.setStartRow(page);
		pi.setEndRow(pi.getStartRow());
		Map<String, Object> param = new HashMap<>();
		param.put("pi", pi);
		param.put("search", search);
		List<Member> memberList = adminMapper.searchMember(param);
		
		Map<String, Object> map = new HashMap<>();
		map.put("pi", pi);
		map.put("memberList", memberList);
		
		return map;
	}

	@Override
	public Map<String, Object> findAllCamp(int page) {
		
		int listCount = adminMapper.getCampListCount();
		
		PageInfo pi = new PageInfo(page, listCount, 10, 10);
		pi.setStartRow(page);
		pi.setEndRow(pi.getStartRow());
		
		List<Camp> campList = adminMapper.findAllCamp(pi);
		
		Map<String, Object> map = new HashMap<>();
		map.put("campList", campList);
		map.put("pi", pi);
		
		return map;
	}

	@Override
	public Map<String, Object> fineCampBySearch(int page, Search search) {
		
		// 1. 검색한 숙소개수
		int listCount = adminMapper.getCampListCountBySearch(search);
		// 2. PageInfo 객체생성
		PageInfo pi = new PageInfo(page, listCount, 10, 10);
		pi.setStartRow(page);
		pi.setEndRow(pi.getStartRow());
		
		Map<String, Object> param = new HashMap<>();
		param.put("pi", pi);
		param.put("search", search);
		// 3. 검색한 숙소목록
		List<Camp> campList = adminMapper.searchCamp(param);
		
		Map<String, Object> map = new HashMap<>();
		map.put("campList", campList);
		map.put("pi", pi);
		
		return map;
	}

	@Override
	public Camp detailCamp(int campNo) {
		
		Camp camp = adminMapper.detailCamp(campNo);
		
		return camp;
	}

	@Override
	@Transactional
	public int deleteCamp(int campNo, int userNo) {
		
		int result = 0;
		
		int result1 = adminMapper.deleteCamp(campNo);
		
		MemberRole mr = new MemberRole();
		mr.setMemberNo(userNo);
		mr.setAuthorityCode(1);
		
		int result2 = adminMapper.updateMemberRole(mr);
		
		if(result1==1 && result2==1) result = 1;
		
		return result;
	}

}
