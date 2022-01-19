package com.cp.campers.mypage.model.vo;

import lombok.Data;

@Data
public class WishCamp {

	private int userNo;		// 찜한 회원
	private int campNo;		// 찜한 캠프
	private String campName;
	private String campAddress;
	private String campPhone;
	private String campStatus;
	private String fileLevel;
	private String fileRoute;
	private String fileName;
}
 