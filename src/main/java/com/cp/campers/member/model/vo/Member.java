package com.cp.campers.member.model.vo;

import java.util.Date;
import java.util.List;

public class Member {
	
	private int userNo;
	private String id;
	private String pwd;
	private String userName;
	private String nickName;
	private String profilePath;
	private String email;
	private String phone;
	private String status;
	private Date createDate;
	private Date modifyDate;
	
	private List<MemberRole> memberRoleList;
	
	public Member() {}

	public Member(int userNo, String id, String pwd, String userName, String nickName, String profilePath, String email,
			String phone, String status, Date createDate, Date modifyDate, List<MemberRole> memberRoleList) {
		super();
		this.userNo = userNo;
		this.id = id;
		this.pwd = pwd;
		this.userName = userName;
		this.nickName = nickName;
		this.profilePath = profilePath;
		this.email = email;
		this.phone = phone;
		this.status = status;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.memberRoleList = memberRoleList;
	}

	public int getUserNo() {
		return userNo;
	}

	public String getId() {
		return id;
	}

	public String getPwd() {
		return pwd;
	}

	public String getUserName() {
		return userName;
	}

	public String getNickName() {
		return nickName;
	}

	public String getProfilePath() {
		return profilePath;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getStatus() {
		return status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public List<MemberRole> getMemberRoleList() {
		return memberRoleList;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public void setMemberRoleList(List<MemberRole> memberRoleList) {
		this.memberRoleList = memberRoleList;
	}

	@Override
	public String toString() {
		return "Member [userNo=" + userNo + ", id=" + id + ", pwd=" + pwd + ", userName=" + userName + ", nickName="
				+ nickName + ", profilePath=" + profilePath + ", email=" + email + ", phone=" + phone + ", status="
				+ status + ", createDate=" + createDate + ", modifyDate=" + modifyDate + ", memberRoleList="
				+ memberRoleList + "]";
	}
	
	
	

}
