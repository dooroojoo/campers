package com.cp.campers.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.board.model.vo.Attachment;
import com.cp.campers.board.model.vo.Board;

@Mapper
public interface BoardMapper {
	
	
	int insertBoard(Board board);
	
	void insertBoardImage(Attachment attachment);
	
	void insertImageNo();
	
	List<Board> selectBoardList();
	
	int deleteBoard(int bid);
	
	int updateBoard(Board board);
	
	Board boardDetail(int bid);



}
