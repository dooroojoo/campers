package com.cp.campers.board.model.vo;

import java.util.List;

import lombok.Data;

@Data
public class Board {

	private String title;
	private String content;
	private int writer;
	private List<Attachment> attachment;
}
