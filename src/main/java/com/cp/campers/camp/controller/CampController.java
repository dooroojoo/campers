package com.cp.campers.camp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cp.campers.camp.model.service.CampService;
import com.cp.campers.camp.model.vo.Room;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/camp")
public class CampController {
	
	private CampService campService;
	
	@Autowired
	public CampController(CampService campService) {
		this.campService = campService;
	}

	@GetMapping("detail")
	public String campDetail(int campNo, Model model) {
		
		Map<String, Object> map = campService.campDetail(campNo);
		model.addAttribute("camp", map.get("camp"));
		model.addAttribute("roomList", map.get("roomList"));
		// model.addAttribute("reviewList", map.get("reviewList"));
		model.addAttribute("newReply", '\n');
		
		log.info(map.get("camp").toString());
		// log.info(map.get("reviewList").toString());
		
		return "camp/campDetail";
	}
	
	@GetMapping("/roomDetail/{roomNo}")
	@ResponseBody
	public Room roomDetail(@PathVariable int roomNo) {
		log.info("조회 요청 roomNo : {}", roomNo);
		
		return campService.roomDetail(roomNo);
	}
	
}
