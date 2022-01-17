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
	private int fileLevel;
	private String fileRoute;
	private String fileName;
	
	/* BusinessType 연결 */
	/* 한 캠프는 여러 사업장 형태를 가질 수 있음 - BUSINESS_TYPE 조인한 결과 값 */
	//private List<Integer> businessType; // 보유 사업장 형태 목록
	private List<CampBusinessType> campBusinessTypeList;
	/* CampFacility 연결 */
	
	/* 한 캠프는 여러 시설을 가질 수 있음 - CAMP_FACILITY*/
	//private List<Integer> facilityNo; // 사업장 시설 목록
	private List<CampFacility> campFacilityList;
	/* Room 연결 
	 * 한개 말고 다수 일시 List로 변경
	 * List<Room> roomList;*/
	private Room room;
	
	/* CampFile 연결 */
	private List<CampFileNo> campFileNoList;
	
	/* RoomFile 연결 */
	private List<RoomFileNo> roomFileNoList;
	
	/* ImageFile 연결 */
	private List<ImageFile> imageFileList;
	
}
