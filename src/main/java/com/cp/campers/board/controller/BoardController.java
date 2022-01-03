package com.cp.campers.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/board")
public class BoardController {

	
	@GetMapping("/list")
	public void boardList() {}
	
	@GetMapping("/detail")
	public void detailList() {}
	
	@GetMapping("/write")
	public void writeBoard() {}
	
	@GetMapping("/mycomment")
	public void myCommentList() {}
	
	@GetMapping("/myboard")
	public void myBoardList() {}
	
	
}
