package com.cp.campers.board.model.service;

import java.util.List;
import java.util.Map;

import com.cp.campers.board.model.vo.Attachment;
import com.cp.campers.board.model.vo.Board;
import com.cp.campers.board.model.vo.Comment;
import com.cp.campers.board.model.vo.Search;

public interface BoardService {
	
	int insertBoard(Board board);
	
	void insertBoardImage(Attachment attachment);
	
	Map<String, Object> selectBoardList(int page);
	
	int deleteBoard(int bid);
	
	int updateBoard(Board board);
	
	Board boardDetail(int bid);

	Map<String, Object> searchBoardList(int page, Search search);

	void insertComment(Comment comment);

	List<Comment> selectCommentList(int bid);

	void insertRefComment(Comment comment);

	List<Comment> selectRefCommentList(int bid);



}
