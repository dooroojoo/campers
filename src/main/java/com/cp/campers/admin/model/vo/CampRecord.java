package com.cp.campers.admin.model.vo;

import java.util.Date;

import lombok.Data;

@Data
public class CampRecord {

	private int crNo;
	private Date recordDate;
	private int campNo;
	private int recordNo;
	private Record record;
}
