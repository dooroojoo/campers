package com.cp.campers.admin.model.vo;

import java.util.Date;

import lombok.Data;
@Data
public class Report {

	private int rNo;
	private int id;				// 게시물, 댓글id
	private String reason;
	private String content;
	private Date reportDate;
	private String reportType;
	private int victim;
	private String vicId;
	private String vicNickName;
	private int suspect;
	private String susId;
	private String susNickName;
	private String reportStatus;
	private int bid;
	private String deleteStatus;
	
//	REPORT_NO	NUMBER
//	REPORT_REASON	VARCHAR2(100 BYTE)
//	REPORT_DATE	DATE
//	REPORT_TYPE	VARCHAR2(10 BYTE)
//	VICTIM	신고자
//	SUSPECT	피신고자
//	REPORT_STATUS	VARCHAR2(1 BYTE)
}
