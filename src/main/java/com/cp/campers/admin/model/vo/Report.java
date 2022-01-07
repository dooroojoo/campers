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
	private int suspact;
	private String reportStatus;
	
}
