package com.cp.campers.main.model.service;

import java.util.List;

import com.cp.campers.main.model.vo.Recommend;

public interface MainService {
	
	// 메인페이지 슬라이드 추천 리스트 조회
	List<Recommend> mainSlider();

	// 메인페이지 예약순 3개 리스트 조회
	List<Recommend> mainBestList();

	// 메인페이지 신규순 3개 리스트 조회
	List<Recommend> mainNewList();
	
	
}
