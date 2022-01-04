package com.cp.campers.reservePayment.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp.campers.reservePayment.model.dao.ReserPayMapper;
import com.cp.campers.reservePayment.model.vo.ReserveInfo;

@Service
public class ReserPayServiceImpl implements ReserPayService{

	private ReserPayMapper reserPayMapper;
	
	@Autowired
	public ReserPayServiceImpl(ReserPayMapper reserPayMapper) {
		this.reserPayMapper = reserPayMapper;
	}
	
	
	@Transactional
	@Override
	public int insertReserPay(ReserveInfo reserveInfo) {
		return reserPayMapper.insertReserPay(reserveInfo);
	}
	
	
	
}
