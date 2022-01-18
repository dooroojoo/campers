package com.cp.campers.mypage.model.vo;

import com.cp.campers.member.model.vo.Member;
import com.cp.campers.reservePayment.model.vo.ReserveInfo;

import lombok.Data;

@Data
public class BusinessReservation{

	private int reserNo;
	private int roomNo;
	private String campName;
	private int campNo;
	private int fileNo;
	private int fileLevel;
	private String fileRoute;
	private String fileName;
	private String roomName;
	private String userName;
	private int userNo;
	private String userPhone;
	private String request;
	private String checkIn;
	private String checkOut;
	
}
