package com.cp.campers.reservePayment.model.service;

import com.cp.campers.reservePayment.model.vo.PaymentInfo;
import com.cp.campers.reservePayment.model.vo.ReserveInfo;

public interface ReserPayService {
	
	// 예약 insert
	int insertReserPay(ReserveInfo reserveInfo,  PaymentInfo paymentInfo);
	
	
}
