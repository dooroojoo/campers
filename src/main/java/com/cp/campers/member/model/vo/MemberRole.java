package com.cp.campers.member.model.vo;

public class MemberRole {
	
	private int memberNo;
	private int authorityCode;
	private Authority authority;
	
	public MemberRole() {}
	
	public MemberRole(int memberNo, int authorityCode, Authority authority) {
		super();
		this.memberNo = memberNo;
		this.authorityCode = authorityCode;
		this.authority = authority;
	}
	
	public int getMemberNo() {
		return memberNo;
	}
	public int getAuthorityCode() {
		return authorityCode;
	}
	public Authority getAuthority() {
		return authority;
	}
	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}
	public void setAuthorityCode(int authorityCode) {
		this.authorityCode = authorityCode;
	}
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "MemberRole [memberNo=" + memberNo + ", authorityCode=" + authorityCode + ", authority=" + authority
				+ "]";
	}
	

	
}
