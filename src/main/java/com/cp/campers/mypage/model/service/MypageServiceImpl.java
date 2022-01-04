package com.cp.campers.mypage.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cp.campers.mypage.model.dao.MypageMapper;
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
		
	}
	
	
}
