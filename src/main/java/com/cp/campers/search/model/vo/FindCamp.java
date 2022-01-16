package com.cp.campers.search.model.vo;


public class FindCamp{

	private String sArea;				// 지역 검색
	private String sIn;					// 체크인 날짜 검색
	private String sOut;				// 체크아웃 날짜 검색
	private String sGuest;				// 인원 수 검색
	private String sName;				// 캠핑장이름 검색
	
	public FindCamp() {}

	public FindCamp(String sArea, String sIn, String sOut, String sGuest, String sName) {
		super();
		this.sArea = sArea;
		this.sIn = sIn;
		this.sOut = sOut;
		this.sGuest = sGuest;
		this.sName = sName;
	}

	public String getsArea() {
		return sArea;
	}

	public void setsArea(String sArea) {
		this.sArea = sArea;
	}

	public String getsIn() {
		return sIn;
	}

	public void setsIn(String sIn) {
		this.sIn = sIn;
	}

	public String getsOut() {
		return sOut;
	}

	public void setsOut(String sOut) {
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

	@Override
	public String toString() {
		return "FindCamp [sArea=" + sArea + ", sIn=" + sIn + ", sOut=" + sOut + ", sGuest=" + sGuest + ", sName="
				+ sName + "]";
	}

	
	
	
	
	
}