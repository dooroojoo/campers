package com.cp.campers.main.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.main.model.vo.Recommend;

@Mapper
public interface MainMapper {
	
	// 메인페이지 슬라이드 추천 리스트 조회
	List<Recommend> mainSlider();
	
	// 메인페이지 예약순 3개 리스트 조회
	List<Recommend> mainBestList();

	// 메인페이지 신규순 3개 리스트 조회
	List<Recommend> mainNewList();

}
