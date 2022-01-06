package com.cp.campers.camp.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.camp.model.vo.Camp;
import com.cp.campers.camp.model.vo.Room;

@Mapper
public interface CampMapper {

	// 숙소 조회
	Camp campDetail(int campNo);

	// 객실 조회
	List<Room> detailRoom(int campNo);

}
