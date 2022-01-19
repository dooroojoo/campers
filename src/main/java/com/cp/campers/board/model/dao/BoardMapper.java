package com.cp.campers.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.board.model.vo.Attachment;
import com.cp.campers.board.model.vo.Board;
import com.cp.campers.board.model.vo.BoardFileNo;
import com.cp.campers.board.model.vo.Comment;
import com.cp.campers.board.model.vo.NextBoard;
import com.cp.campers.board.model.vo.PageInfo;
import com.cp.campers.board.model.vo.PrevBoard;
import com.cp.campers.board.model.vo.Search;

@Mapper
public interface BoardMapper {
	
	
	int insertBoard(Board board);
	
	void insertBoardImage(Attachment attachment);
	
	void insertImageNo();
	
	List<Board> selectBoardList(PageInfo pi);
	
	List<Board> selectThumbnailList();
	
	void deleteBoard(int bid);
	
	void updateBoard(Board board);
	
	Board boardDetail(int bid);

	int getListCount();

	int getListCountBySearch(Search search);

	List<Board> searchBoardList(Map<String, Object> param);

	void insertComment(Comment comment);

	List<Comment> selectCommentList(int bid);

	void insertRefComment(Comment comment);

	PrevBoard selectPrevBoard(int bid);

	NextBoard selectNextBoard(int bid);

	List<BoardFileNo> selectBoardImage(int bid);

	int selectBid();

	Board selectBoardUpdate(int bid);

	int increaseCount(int bid);

	int getListCountMyComment(int writer);

	int getListCountMyBoard(int writer);

	List<Board> selectMyBoardList(Map<String, Object> param);

	List<Comment> selectMyCommentList(Map<String, Object> param);

	void commentDelete(int cid);

	void boardLikeUp(Map<String, Object> param);

	String selectLikeCount(Object object);

	String selectLikedBid(Map<String, Object> bidAndUserNo);

	void boardLikeDown(Map<String, Object> param);

	int updateBoardImage(Map<String, Object> param);

	int insertAddedPhoto(Map<String, Object> param);

	void insertAddedImageNo(Map<String, Object> param);

	void deleteImgs(String deleteImg);

	void updateFileLevel(String changeName);


}
