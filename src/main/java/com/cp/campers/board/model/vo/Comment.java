package com.cp.campers.board.model.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Comment {

	private int cid;
	private int bid;
	private String cContent;
	private Date createDate;
	private Date modifyDate;
	private String status;
	private int refCid;
	private int cWriter;
	private String refWriter;
	private String nickName;
	private String profilePath;
	private int refOriginCid;
	private String title;
	
}
