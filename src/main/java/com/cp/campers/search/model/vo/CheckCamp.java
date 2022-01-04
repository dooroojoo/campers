package com.cp.campers.search.model.vo;

import lombok.Data;

@Data
public class CheckCamp {
		
		// 지역명 검색
		private String searchArea;

		// 날짜 검색
		private String searchDate;
		
		// 인원수 검색
		private String searchGuest;
		
		// 캠핑장 이름 검색
		private String searchName;
		
		// 캠핑장 타입 검색
		private String searchType;
		
		// 캠핑장 시설 검색
		private String searchFacility;
		
		// 캠핑장 바닥 검색
		private String searchFloor;
		

	}

