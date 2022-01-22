package com.cp.campers.reservePayment.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp.campers.reservePayment.model.dao.ReserPayMapper;
import com.cp.campers.reservePayment.model.vo.PaymentInfo;
import com.cp.campers.reservePayment.model.vo.ReserveInfo;

@Service
public class ReserPayServiceImpl implements ReserPayService{

   private ReserPayMapper reserPayMapper;
   
   @Autowired
   public ReserPayServiceImpl(ReserPayMapper reserPayMapper) {
      this.reserPayMapper = reserPayMapper;
   }
   
   
   @Transactional
   @Override
   public int insertReserPay(ReserveInfo reserveInfo, PaymentInfo paymentInfo) {
      
      int insertReserve =  reserPayMapper.insertReserve(reserveInfo);
      
      int insertPayment = reserPayMapper.insertPayment(paymentInfo);
      
      int result;
      
      if(insertReserve > 0 && insertPayment > 0) {
         result = 1;
      } else {
         result = 0;
      }
      
      return result;
   }
   
   
   
}