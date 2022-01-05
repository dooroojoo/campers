package com.cp.campers.board.model.service;

import java.util.Map;

import com.cp.campers.board.model.vo.Attachment;
import com.cp.campers.board.model.vo.Board;

public interface BoardService {
	
	int insertBoard(Board board);
	
	void insertBoardImage(Attachment attachment);
	
	Map<String, Object> selectBoardList(int page);
	
	int deleteBoard(int bid);
	
	int updateBoard(Board board);
	
	Board boardDetail(int bid);



}
