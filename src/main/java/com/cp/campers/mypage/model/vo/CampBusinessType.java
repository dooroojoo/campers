package com.cp.campers.mypage.model.vo;

import lombok.Data;
 
@Data
public class CampBusinessType {

	private int campNo;					// 숙소 번호
	private int businessNo;				// 사업장 번호
	private BusinessType businessType;	// 사업장 타입
	
}
