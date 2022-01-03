package com.cp.campers.reservePayment.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ReserveInfo {

	private int reserNo; 			// 예약번호
	private int userNo;  			// 회원번호
	private String reserName; 		// 예약자명
	private String phone; 			// 핸드폰번호
	private String request;			// 예약요청사항
	private Date checkIn; 			// 체크인
	private Date checkOut; 			// 체크아웃
	private String accountNum;  	// 환불계좌번호
	private String account_hol;     // 예금주
	private String bank;  			// 은행
	private int roomQua; 			// 예약객실수량
	private int roomNo;			 	// 객실번호
	
	
}
