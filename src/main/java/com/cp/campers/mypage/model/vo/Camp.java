package com.cp.campers.mypage.model.vo;

import java.util.List;

import lombok.Data;

@Data
public class Camp {

	private int campNo;
	private String campName;
	private int userNo;
	private String campAddress;
	private String campPhone;
	private String campIntro;
	private String campPath;
	private String campPolicy;
	private String campStatus;
	private String accountNum;
	private String bank;
	private String checkin;
	private String checkout;
	private String refusal;
	
	/* BusinessType 연결 */
	/* 한 캠프는 여러 사업장 형태를 가질 수 있음 - BUSINESS_TYPE 조인한 결과 값 */
	private List<BusinessType> BusinessTypeList; // 보유 사업장 형태 목록
	/* CampFacility 연결 */
	private List<CampFacilityRole> campFacilityRoleList;
}
