package com.cp.campers.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.board.model.vo.Attachment;
import com.cp.campers.board.model.vo.Board;
import com.cp.campers.board.model.vo.Comment;
import com.cp.campers.board.model.vo.PageInfo;
import com.cp.campers.board.model.vo.Search;

@Mapper
public interface BoardMapper {
	
	
	int insertBoard(Board board);
	
	void insertBoardImage(Attachment attachment);
	
	void insertImageNo();
	
	List<Board> selectBoardList(PageInfo pi);
	
	List<Board> selectThumbnailList();
	
	int deleteBoard(int bid);
	
	int updateBoard(Board board);
	
	Board boardDetail(int bid);

	int getListCount();

	int getListCountBySearch(Search search);

	List<Board> searchBoardList(Map<String, Object> param);

	void insertComment(Comment comment);

	List<Comment> selectCommentList(int bid);

	void insertRefComment(Comment comment);

	List<Comment> selectRefCommentList(int bid);


}
