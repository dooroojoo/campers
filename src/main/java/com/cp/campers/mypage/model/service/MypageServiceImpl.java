package com.cp.campers.mypage.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.campers.admin.model.vo.PageInfo;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.mypage.model.dao.MypageMapper;
import com.cp.campers.mypage.model.vo.BusinessType;
import com.cp.campers.mypage.model.vo.Camp;

@Service
public class MypageServiceImpl implements MypageService{

	private final MypageMapper mypageMapper;
	
	@Autowired
	public MypageServiceImpl(MypageMapper mypageMapper) {
		this.mypageMapper = mypageMapper;
	}

	@Override
	public void mypageCampEnrollment(Camp camp) {
		
		mypageMapper.insertCamp(camp);
		
		BusinessType businessType = new BusinessType();
		mypageMapper.insertBusinessType(businessType);
	}
	
	@Override
	public List<Member> selectMember(Member member){
		return mypageMapper.selectMember(member);
	}

	@Override
	public Map<String, Object> findAllMember(int page) {
		
		// 1. 총 회원수
		int listCount = mypageMapper.getListCount();
		
		PageInfo pi = new PageInfo(page, listCount, 10, 10);
		pi.setStartRow(page);
		pi.setEndRow(pi.getStartRow());
		List<Member> memberList = mypageMapper.findAllMember(pi);
		
		Map<String, Object> map = new HashMap<>();
		map.put("pi", pi);
		map.put("memberList", memberList);
				
		return map;
	}

	@Override
	public List<Member> findMember() {
		
		return null;
	}
	
}
