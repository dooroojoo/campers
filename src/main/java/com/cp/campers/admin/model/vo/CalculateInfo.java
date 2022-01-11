package com.cp.campers.admin.model.vo;


import lombok.Data;

@Data
public class CalculateInfo {
	
	private int campNo;      	 // 숙소번호
	private String userName; 	 // 사업자명 
	private String accountNum; 	 // 사업자계좌번호
	private String bank;		 // 은행
	private int msa; 			 // 월별판매금액
	private int msma;    		 // 월별정산금액
	
	public CalculateInfo(int campNo, String userName, String accountNum, String bank, int msa, int msma) {
		super();
		this.campNo = campNo;
		this.userName = userName;
		this.accountNum = accountNum;
		this.bank = bank;
		this.msa = msa;
		this.msma = msma;
	}

	public CalculateInfo(int campNo, String userName, String accountNum, String bank) {
		super();
		this.campNo = campNo;
		this.userName = userName;
		this.accountNum = accountNum;
		this.bank = bank;
	}
	
	
	
	
	
}
