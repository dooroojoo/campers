package com.cp.campers.member.model.vo;

public class Authority {
	
	private int authorityCode;	
	private String authorityName;
	private String authorityDesc;
	
	public Authority() {}
	
	public Authority(int authorityCode, String authorityName, String authorityDesc) {
		super();
		this.authorityCode = authorityCode;
		this.authorityName = authorityName;
		this.authorityDesc = authorityDesc;
	}

	public int getAuthorityCode() {
		return authorityCode;
	}
	public String getAuthorityName() {
		return authorityName;
	}
	public String getAuthorityDesc() {
		return authorityDesc;
	}
	public void setAuthorityCode(int authorityCode) {
		this.authorityCode = authorityCode;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	public void setAuthorityDesc(String authorityDesc) {
		this.authorityDesc = authorityDesc;
	}
	@Override
	public String toString() {
		return "Authority [authorityCode=" + authorityCode + ", authorityName=" + authorityName + ", authorityDesc="
				+ authorityDesc + "]";
	}
	
	

}
