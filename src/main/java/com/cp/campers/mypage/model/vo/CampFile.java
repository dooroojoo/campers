package com.cp.campers.mypage.model.vo;

import lombok.Data;
 
@Data
public class CampFile {

	private int campNo;				// 캠프 번호
	private int fileNo;				// 파일 번호
	private ImageFile imageFile;	// 이미지 파일
}
