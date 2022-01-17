package com.cp.campers.mypage.model.vo;

import java.util.Date;
import java.util.List;

import com.cp.campers.board.model.vo.BoardFileNo;

import lombok.Data;
 
@Data
public class Board {

	private String bid;
	private String title;
	private String content;
	private Date createDate;
	private Date modifyDate;
	private String status;
	private int writer;
	private int bcount;
	private int blike;
	private String nickName;
	private int fileLevel;
	private String fileRoute;
	private String fileName;
	
	private int prevId;
	private String prevTitle;
	private int nextId;
	private String nextTitle;
	
	private List<BoardFileNo> boardFileNoList;
	
}
