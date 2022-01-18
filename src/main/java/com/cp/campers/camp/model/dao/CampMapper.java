package com.cp.campers.camp.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.camp.model.vo.Camp;
import com.cp.campers.camp.model.vo.Review;
import com.cp.campers.camp.model.vo.Room;

@Mapper
public interface CampMapper {

	// 숙소 조회
	Camp campDetail(int campNo);

	// 객실목록 조회
	List<Room> roomList(int campNo);

	// 객실 조회
	Room roomDetail(int roomNo);
	
	// 리뷰 조회
	List<Review> reviewList(int campNo);

	// 리뷰 등록
	void insertReview(Review review);
	
	// 리뷰작성 여부
	void reserveWrite(int reserNo);

	// 리뷰 삭제
	void reviewDelete(int rid);

	// 예약건수 조회
	int reserveCount(Map<String, Object> param);

}
