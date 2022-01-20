package com.cp.campers.camp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cp.campers.camp.model.service.CampService;
import com.cp.campers.camp.model.vo.Review;
import com.cp.campers.camp.model.vo.Room;
import com.cp.campers.member.model.vo.UserImpl;
import com.cp.campers.mypage.model.service.MypageService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/camp")
public class CampController {
	
	private CampService campService;
	private MypageService mypageService;
	
	@Autowired
	public CampController(CampService campService, MypageService mypageService) {
		this.campService = campService;
		this.mypageService = mypageService;
	}
	
	/* 숙소 상세 */
	@GetMapping("detail")
	public String campDetail(int campNo, String dateIn, String dateOut,
			@AuthenticationPrincipal UserImpl loginUser, Model model) {
				
		Map<String, Object> map = campService.campDetail(campNo, dateIn, dateOut);
		
		model.addAttribute("camp", map.get("camp"));
		model.addAttribute("roomList", map.get("roomList"));
		model.addAttribute("reviewList", map.get("reviewList"));
		model.addAttribute("newReply", '\n');

		log.info("camp : {}", map.get("camp"));
		// log.info(map.get("reviewList").toString());
		// log.info("reserveCount : ", map.get("reserveCount"));
		log.info("reserveCount : ", map.get("reserveCount"));

		
		if (map.get("roomList").toString().equals("[]")) {
			model.addAttribute("noRoom", "예약할 수 있는 객실이 없어요!");
		}
		
		if (map.get("reviewList").toString().equals("[]")) {
			model.addAttribute("noReview", "아직 리뷰가 없어요!");
		}
		
		/* 찜 기능 */
		Map<String, Object> campNoAndUserNo = new HashMap<>();
		campNoAndUserNo.put("campNo", campNo);
		
		if(loginUser != null) {
			
			campNoAndUserNo.put("userNo", loginUser.getUserNo());
			String likedCampNo = mypageService.selectLikedCampNo(campNoAndUserNo);
			model.addAttribute("likedCampNo", likedCampNo);
		}
		
		// 이 게시물의 좋아요 개수
		String likeCounts = mypageService.selectLikeCount(campNo);
		model.addAttribute("likeCounts",likeCounts);
		
		
		return "camp/campDetail";
	}
	
	/* 객실 상세 : ajax */
	@GetMapping("/roomDetail/{roomNo}")
	@ResponseBody
	public Room roomDetail(@PathVariable int roomNo) {
		log.info("조회 요청 roomNo : {}", roomNo);
		
		return campService.roomDetail(roomNo);
	}
	
	@GetMapping("/review")
	public void insertReview() {}
	
	/* 리뷰 등록 */
	@PostMapping("/review")
	public String insertReview(Review review) {
		log.info("입력 요청 review : {}", review);
		
		campService.insertReview(review);
		
		return "redirect:/mypage/mypageGuestReserve";
	}
	
	/* 리뷰 삭제 */
	@GetMapping("/reviewDelete")
	public String reviewDelete(int rid, int campNo) {
		
		Date today = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		
		campService.reviewDelete(rid);
		
		return "redirect:/camp/detail?campNo=" + campNo + "&dateIn=" + date.format(today) + "&dateOut=" + date.format(today) + "&guest=0";
	}
	
	/* 찜 */
	@GetMapping("/likeUp/{campNo}") 
	 @ResponseBody
	 public String campLikeUp(@PathVariable int campNo, @AuthenticationPrincipal UserImpl loginUser){
		 
		 Map<String, Object> param = new HashMap<>(); 
		 param.put("campNo", campNo);
		 param.put("userNo", loginUser.getUserNo()); 
		 
		 log.info("campNo="+campNo);
		 log.info("userNo"+loginUser.getUserNo());
		 
		 String count = mypageService.campLikeUp(param);
		 
		 log.info("count="+count);
		 
		 return count;
		 
	 }
	
	/* 찜 삭제 */
	@GetMapping("/likeDown/{campNo}")
	 @ResponseBody
	 public String campLikeDown(@PathVariable int campNo, @AuthenticationPrincipal UserImpl loginUser) {
		 Map<String, Object> param = new HashMap<>(); 
		 param.put("campNo", campNo);
		 param.put("userNo", loginUser.getUserNo()); 
		 
		 String count = mypageService.campLikeDown(param);
		 
		 return count;
	 }
	
}
