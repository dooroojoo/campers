package com.cp.campers.search.model.vo;

import lombok.Data;

@Data
public class SearchCamp {
	
	private String cName;		// 숙소이름
	private String cAddress;	// 숙소주소
	private int rMember;		// 객실최대인원(ROOM테이블참조)
	private int rPrice;			// 객실가격(ROOM테이블참조)
	private int rCount;			// 잔여객실수량(ROOM테이블참조)
	private String fileRoute;	// 파일경로(FILE테이블참조)
	private String fileName;	// 파일이름(FILE테이블참조)

}
