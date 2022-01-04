package com.cp.campers.reservePayment.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.reservePayment.model.vo.ReserveInfo;

@Mapper
public interface ReserPayMapper {

	int insertReserPay(ReserveInfo reserveInfo);

}
