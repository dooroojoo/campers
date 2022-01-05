package com.cp.campers.camp.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.campers.camp.model.dao.CampMapper;
import com.cp.campers.camp.model.vo.Camp;

@Service("campService")
public class CampServiceImpl implements CampService{
	
	private CampMapper campMapper;
	
	@Autowired
	public CampServiceImpl(CampMapper campMapper) {
		this.campMapper = campMapper;
	}

	@Override
	public Camp campDetail(int campNo) {
		return null;
	}

}
