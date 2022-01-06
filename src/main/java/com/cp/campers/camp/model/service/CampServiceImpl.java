package com.cp.campers.camp.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.campers.camp.model.dao.CampMapper;
import com.cp.campers.camp.model.vo.Camp;
import com.cp.campers.camp.model.vo.Room;

@Service("campService")
public class CampServiceImpl implements CampService{
	
	private CampMapper campMapper;
	
	@Autowired
	public CampServiceImpl(CampMapper campMapper) {
		this.campMapper = campMapper;
	}

	/* 숙소 조회 */
	@Override
	public Map<String, Object> campDetail(int campNo) {
		
		Camp camp = campMapper.campDetail(campNo);
		
		List<Room> roomList = campMapper.detailRoom(campNo);
		
		Map<String, Object> map = new HashMap<>();
		map.put("camp", camp);
		map.put("roomList", roomList);
		
		return map;
	}

	
}
