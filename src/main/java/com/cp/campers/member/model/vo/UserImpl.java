package com.cp.campers.member.model.vo;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class UserImpl extends User{


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
	
	public UserImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public void setDetails(Member member) {
		this.userNo = member.getUserNo();
		this.id = member.getId();
		this.pwd = member.getPwd();
		this.userName = member.getUserName();
		this.nickName = member.getNickName();
		this.profilePath = member.getProfilePath();
		this.email = member.getEmail();
		this.phone = member.getPhone();
		this.status = member.getStatus();
		this.createDate = member.getCreateDate();
		this.modifyDate = member.getModifyDate();
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


	

	
	
}
