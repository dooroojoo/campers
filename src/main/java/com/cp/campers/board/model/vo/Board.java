package com.cp.campers.board.model.vo;

import java.util.Date;
import java.util.List;

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
	private String nickName;
	private int fileLevel;
	private String fileRoute;
	private String fileName;
	private List<BoardFileNo> boardFileNoList;
	
}
