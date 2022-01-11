package com.cp.campers.admin.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp.campers.admin.model.dao.AdminMapper;
import com.cp.campers.admin.model.vo.CalculateInfo;
import com.cp.campers.admin.model.vo.PageInfo;
import com.cp.campers.admin.model.vo.Report;
import com.cp.campers.admin.model.vo.Search;
import com.cp.campers.camp.model.vo.Camp;
import com.cp.campers.camp.model.vo.Room;
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
		pi.setStartRow(page, pi.getUserLimit());
		pi.setEndRow(pi.getStartRow(), pi.getUserLimit());
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
		pi.setStartRow(page, pi.getUserLimit());
		pi.setEndRow(pi.getStartRow(), pi.getUserLimit());
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
		pi.setStartRow(page, pi.getUserLimit());
		pi.setEndRow(pi.getStartRow(), pi.getUserLimit());
		
		List<Camp> campList = adminMapper.findAllCamp(pi);
		
		Map<String, Object> map = new HashMap<>();
		map.put("campList", campList);
		map.put("pi", pi);
		
		return map;
	}

	@Override
	public Map<String, Object> findCampBySearch(int page, Search search) {
		
		// 1. 검색한 숙소개수
		int listCount = adminMapper.getCampListCountBySearch(search);
		// 2. PageInfo 객체생성
		PageInfo pi = new PageInfo(page, listCount, 10, 10);
		pi.setStartRow(page, pi.getUserLimit());
		pi.setEndRow(pi.getStartRow(), pi.getUserLimit());
		
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
	public Map<String, Object> detailCamp(int campNo) {
		
		Camp camp = adminMapper.detailCamp(campNo);
		
		List<Room> roomList = adminMapper.detailRoom(campNo);
		
		Map<String, Object> map = new HashMap<>();
		map.put("camp", camp);
		map.put("roomList", roomList);
		
		return map;
	}

	@Override
	@Transactional
	public int deleteCamp(int campNo, int userNo) {
		
		int result = 0;
		
		// 1. 일반권한
		MemberRole mr = new MemberRole();
		mr.setMemberNo(userNo);
		mr.setAuthorityCode(1);
		int result1 = adminMapper.updateMemberRole(mr);
		
		// 2. 숙소삭제
		int result2 = adminMapper.deleteCamp(campNo);
		
		if(result1==1 && result2==1) result = 1;
		
		return result;
	}

	@Override
	@Transactional
	public void refusal(int campNo, int userNo, String refusal) {
		
		// 1. 사업자권한
		MemberRole mr = new MemberRole();
		mr.setMemberNo(userNo);
		mr.setAuthorityCode(2);
		adminMapper.updateMemberRole(mr);
		
		// 2. 거절
		Map<String, Object> param = new HashMap<>();
		param.put("refusal", refusal);
		param.put("campNo", campNo);
		adminMapper.refusal(param);
	}

	@Override
	@Transactional
	public void enroll(int campNo, int userNo) {
		
		// 1. 사업자권한
		MemberRole mr = new MemberRole();
		mr.setMemberNo(userNo);
		mr.setAuthorityCode(2);
		adminMapper.updateMemberRole(mr);
		
		// 2. 등록
		adminMapper.enroll(campNo);
		
		// 3. 이력
		adminMapper.recordToEnroll(campNo);
	}

	@Override
	@Transactional
	public int insertReport(Report report) {
		int result = 0;
		
		// 1. 신고 처리 => 기타일 경우 신고 내용을 담음
		if(report.getReason().equals("기타")) {
			report.setReason("[기타] " + report.getContent());
		}
		
		int rnum1 = adminMapper.insertReport(report);
		
		// 2. 게시물/댓글 구분해서 인서트
		int rnum2 = 0;
		
		if(report.getReportType().equals("board")) {
			rnum2 = adminMapper.insertReportByBoard(report.getId());
		} else {
			rnum2 = adminMapper.insertReportByReply(report.getId());
		}
		
		
		
		if(rnum1 != 0 && rnum2 != 0) {
			result = 1;
		}
		
		return result;
	}

	@Override
	public Map<String, Object> findAllReport(int page) {
		
		// 1. 총 신고수
		int listCount = adminMapper.getReportCount();
		// 2. PageInfo 객체
		PageInfo pi = new PageInfo(page, listCount, 10, 10);
		// 3. 페이징 처리된 신고목록
		pi.setStartRow(page, pi.getUserLimit());
		pi.setEndRow(pi.getStartRow(), pi.getUserLimit());
		List<Report> reportList = adminMapper.findAllReport(pi);
		
		Map<String, Object> map = new HashMap<>();
		map.put("pi", pi);
		map.put("reportList", reportList);
		
		return map;
	}

	@Override
	@Transactional
	public int completeReport(int rNo) {
		return adminMapper.completeReport(rNo);
	}

//	@Override
//	public Map<String, Object> calculateList(int page) {
//		
//		// 1. 게시글 총 개수 구하기 
//		int listCount = adminMapper.getcalculateListCount();
//		
//		PageInfo pi = new PageInfo(page, listCount, 10, 10);
//		pi.setStartRow(page, pi.getUserLimit());
//		pi.setEndRow(pi.getStartRow(), pi.getUserLimit());
//		
//		List<CalculateInfo> calculateList = adminMapper.calculateList(pi);
//		
//		Map<String, Object> map = new HashMap<>();
//		map.put("calculateList", calculateList);
//		map.put("pi", pi);
//		
//		return map;
//	}



}
