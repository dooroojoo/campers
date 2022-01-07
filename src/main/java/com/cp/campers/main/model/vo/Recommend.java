package com.cp.campers.main.model.vo;

import lombok.Data;

@Data
public class Recommend {
	
	private int cRNo;			// 캠핑장코드
	private String cRName;		// 캠핑장명
	private String cRAddress;	// 캠핑장주소
	private String fRRoute;		// 캠핑장썸네일이미지경로
	private String fRName;		// 캠핑장썸네일이미지명
	
	

}
