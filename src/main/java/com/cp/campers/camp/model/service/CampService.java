package com.cp.campers.camp.model.service;

import java.util.Map;

import com.cp.campers.camp.model.vo.Room;

public interface CampService {

	// 숙소 상세
	Map<String, Object> campDetail(int campNo);

	Room roomDetail(int roomNo);
}
