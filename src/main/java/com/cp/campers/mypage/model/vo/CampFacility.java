package com.cp.campers.mypage.model.vo;

import lombok.Data;
 
@Data
public class CampFacility {

	private int campNo;			// 숙소 번호
	private int facilityNo;		// 시설 번호
	private Facility facility;	// 시설
}
 