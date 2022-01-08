package com.cp.campers.camp.model.service;

import java.util.Map;

import com.cp.campers.camp.model.vo.Room;

public interface CampService {

	// 숙소 상세
	Map<String, Object> campDetail(int campNo);

	// 객실 상세
	Room roomDetail(int roomNo);

	// 리뷰 삭제
	void reviewDelete(int rid);
}
