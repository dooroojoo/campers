package com.cp.campers.reservePayment.model.service;

import org.springframework.stereotype.Service;

import com.cp.campers.reservePayment.model.vo.ReserveInfo;

public interface ReserPayService {
	
	// 예약 insert
	int insertReserPay(ReserveInfo reserveInfo);

	
}
