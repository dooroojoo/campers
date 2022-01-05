package com.cp.campers.camp.model.vo;

import java.util.List;

import com.cp.campers.admin.model.vo.CampRecord;
import com.cp.campers.mypage.model.vo.Room;

import lombok.Data;

@Data
public class Camp {

	private int campNo;
	private String campName;
	private int userNo;
	private String userName;
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
	private List<CampRecord> campRecordList;
	private List<Review> reviewList;
	private List<ImageFile> imgFileList;
}
