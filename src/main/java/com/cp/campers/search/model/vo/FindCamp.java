package com.cp.campers.search.model.vo;

import java.util.Date;
import java.util.List;


public class FindCamp{

	private String sArea;			// 지역 검색
	private Date sIn;				// 체크인 날짜 검색
	private Date sOut;				// 체크아웃 날짜 검색
	private String sGuest;			// 인원 수 검색
	private String sName;			// 캠핑장이름 검색
	private List<String> sType;		// 캠핑장 타입 검색
	private List<String> sFaci;		// 캠핑장 시설 검색
	private List<String> sFloor;	// 캠핑장 바닥 검색
	

	public FindCamp() {}

	public FindCamp(String sArea, Date sIn, Date sOut, String sGuest, String sName, List<String> sType,
			List<String> sFaci, List<String> sFloor) {
		super();
		this.sArea = sArea;
		this.sIn = sIn;
		this.sOut = sOut;
		this.sGuest = sGuest;
		this.sName = sName;
		this.sType = sType;
		this.sFaci = sFaci;
		this.sFloor = sFloor;
	}


	public String getsArea() {
		return sArea;
	}


	public void setsArea(String sArea) {
		this.sArea = sArea;
	}


	public Date getsIn() {
		return sIn;
	}


	public void setsIn(Date sIn) {
		this.sIn = sIn;
	}


	public Date getsOut() {
		return sOut;
	}


	public void setsOut(Date sOut) {
		this.sOut = sOut;
	}


	public String getsGuest() {
		return sGuest;
	}


	public void setsGuest(String sGuest) {
		this.sGuest = sGuest;
	}


	public String getsName() {
		return sName;
	}


	public void setsName(String sName) {
		this.sName = sName;
	}


	public List<String> getsType() {
		return sType;
	}


	public void setsType(List<String> sType) {
		this.sType = sType;
	}


	public List<String> getsFaci() {
		return sFaci;
	}


	public void setsFaci(List<String> sFaci) {
		this.sFaci = sFaci;
	}


	public List<String> getsFloor() {
		return sFloor;
	}


	public void setsFloor(List<String> sFloor) {
		this.sFloor = sFloor;
	}


	@Override
	public String toString() {
		return "FindCamp [sArea=" + sArea + ", sIn=" + sIn + ", sOut=" + sOut + ", sGuest=" + sGuest + ", sName="
				+ sName + ", sType=" + sType + ", sFaci=" + sFaci + ", sFloor=" + sFloor + "]";
	}
	
	
	
	

}
