package com.cp.campers.search.model.vo;


import java.util.Date;

import lombok.Data;

@Data
public class SearchCamp {
	
	private int cNo;			// 숙소코드
	private String cName;		// 숙소이름
	private String cAddress;	// 숙소주소
	private int rCount;			// 잔여객실수량(ROOM테이블참조)
	private String revStatus;	// 예약현황(Y,N/REVERSE테이블참조)
	private Date checkIn;		// 입실일(REVERSE테이블참조)
	private Date checkOut;		// 퇴실일(REVERSE테이블참조)
	private int maxGuest;		// 객실최대인원(ROOM테이블참조)
	private int minPrice;		// 객실최소가격(ROOM테이블참조)
	private int maxPrice;		// 객실최대가격(ROOM테이블참조)
	private String fileRoute;	// 파일경로(FILE테이블참조)
	private String fileName;	// 파일이름(FILE테이블참조)

}
