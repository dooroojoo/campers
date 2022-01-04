package com.cp.campers.reservePayment.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveInfo {

//	private int reserNo; 			// 예약번호
//	private int userNo;  			// 회원번호
	private String reserName; 		// 예약자명
	private String phone; 			// 핸드폰번호
	private String request;			// 예약요청사항
	private String checkIn; 		// 체크인
	private String checkOut; 		// 체크아웃
	private String accountNum;  	// 환불계좌번호
	private String accountHol;      // 예금주
	private String bank;  			// 은행
	private int roomQua; 			// 예약객실수량
//	private int roomNo;			 	// 객실번호
	
	
}
