package com.cp.campers.search.model.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class FindCamp {
	private String sArea;			// 지역 검색
	private Date sIn;				// 체크인 날짜 검색
	private Date sOut;				// 체크아웃 날짜 검색
	private String sGuest;			// 인원 수 검색
	private String sName;			// 캠핑장이름 검색
	private List<String> sType;		// 캠핑장 타입 검색
	private List<String> sFaci;		// 캠핑장 시설 검색
	private List<String> sFloor;	// 캠핑장 바닥 검색
	
	

}
