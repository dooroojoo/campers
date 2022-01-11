package com.cp.campers.board.model.vo;

import lombok.Data;

@Data
public class Attachment {
	private int fileNo;
	private String fileName;
	private String fileRoute;
	private String status;
	private int fileLevel;
	private String fileNewName;
}
