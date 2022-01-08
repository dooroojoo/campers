package com.cp.campers.main.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.campers.main.model.dao.MainMapper;
import com.cp.campers.main.model.vo.Recommend;

@Service("mainService")
public class MainServiceImpl implements MainService {

	private final MainMapper mainMapper;
	
	@Autowired
	public MainServiceImpl(MainMapper mainMapper) {
		this.mainMapper = mainMapper;
	}
	
	@Override
	public List<Recommend> mainSlider() {
		return mainMapper.mainSlider();
	}

	@Override
	public List<Recommend> mainBestList() {
		return mainMapper.mainBestList();
	}

	@Override
	public List<Recommend> mainNewList() {
		return mainMapper.mainNewList();
	}

}
