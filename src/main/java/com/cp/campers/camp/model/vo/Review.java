package com.cp.campers.camp.model.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Review {

	private int rid;
	private int writer;
	private String rcontent;
	private Date createDate;
	private String status;
	private int reserNo;
	private int campNo;
}
