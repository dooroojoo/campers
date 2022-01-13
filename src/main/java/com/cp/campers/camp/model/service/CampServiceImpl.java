package com.cp.campers.camp.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp.campers.camp.controller.CampController;
import com.cp.campers.camp.model.dao.CampMapper;
import com.cp.campers.camp.model.vo.Camp;
import com.cp.campers.camp.model.vo.Review;
import com.cp.campers.camp.model.vo.Room;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service("campService")
public class CampServiceImpl implements CampService{
	
	private CampMapper campMapper;
	
	@Autowired
	public CampServiceImpl(CampMapper campMapper) {
		this.campMapper = campMapper;
	}

	/* 숙소 조회 */
	@Override
	public Map<String, Object> campDetail(int campNo, String dateIn, String dateOut) {
		
		Camp camp = campMapper.campDetail(campNo);
		
		List<Room> roomList = campMapper.roomList(campNo);
		
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String, Object> param = new HashMap<>();
		for(Room room : roomList) {
			param.put("dateIn", dateIn);
			param.put("dateOut", dateOut);
			param.put("roomNo",room.getRoomNo());
			
			Map<String, Object> countparam = new HashMap<>();
			String count = campMapper.reserveCount(param);
			countparam.put("count", count);
			list.add(countparam);
			
		}
		for(Map<String, Object> li : list) {
			log.info("list"+li.toString());
		}
		
		/*
		 * List<Map<String, Object>> list = new ArrayList<>();
		 * 
		 * List<Integer> reserveCount = new ArrayList<>();
		 * 
		 * Map<String, Object> param = new HashMap<>(); param.put("dateIn", dateIn);
		 * param.put("dateOut", dateOut); for(int i = 0; i < roomList.size(); i++) {
		 * //log.info("roomList : ", roomList); param.put("roomNo",
		 * roomList.get(i).getRoomNo()); // log.info("roomNo : ",
		 * roomList.get(i).getRoomNo()); list.add(param);
		 * reserveCount.add(campMapper.reserveCount(param)); param.remove("roomNo"); //
		 * log.info("reserveCount : ", reserveCount); // log.info("list : ", list); }
		 */
		
		List<Review> reviewList = campMapper.reviewList(campNo);
		
		Map<String, Object> map = new HashMap<>();
		map.put("camp", camp);
		map.put("roomList", roomList);
		map.put("reviewList", reviewList);
		//map.put("reserveCount", reserveCount);
		
		return map;
	}

	/* 객실 조회 */
	@Override
	public Room roomDetail(int roomNo) {
		return campMapper.roomDetail(roomNo);
	}

	/* 리뷰 등록 */
	@Override
	@Transactional
	public void insertReview(Review review) {
		campMapper.insertReview(review);
	}
	
	/* 리뷰 삭제 */
	@Override
	@Transactional
	public void reviewDelete(int rid) {
		campMapper.reviewDelete(rid);
	}


	
}
