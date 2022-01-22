package com.cp.campers.reservePayment.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cp.campers.member.model.vo.UserImpl;
import com.cp.campers.reservePayment.model.service.ReserPayService;
import com.cp.campers.reservePayment.model.vo.PaymentInfo;
import com.cp.campers.reservePayment.model.vo.ReserveInfo;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class ReserPayController {

private ReserPayService reserPayService;
   
   @Autowired
   public ReserPayController(ReserPayService reserPayService) {
      this.reserPayService = reserPayService;
   }
   
   
   @GetMapping("/reserPay")
   public String reserPay(Model model, @AuthenticationPrincipal UserImpl user) {
         
      model.addAttribute("userName", user.getUserName());
      
       log.info("userName : {} ", user.getUserName());
      
      return "reservePayment/reserPay";
   }
   
   /* 예약 및 결제 insert */
   @ResponseBody
   @PostMapping("/reserPay/insert")
   public Map<String, String> insertReserPay(@ModelAttribute ReserveInfo reserveInfo, 
         @ModelAttribute PaymentInfo paymentInfo, @AuthenticationPrincipal UserImpl user, Model model) {
      
      log.info("예약 정보 : {}", reserveInfo);
      log.info("결제 정보 : {}", paymentInfo);
      
      reserveInfo.setUserNo(user.getUserNo());
      
      String msg = reserPayService.insertReserPay(reserveInfo, paymentInfo) > 0 ? "예약 및 결제가 완료되었습니다." : "예약 및 결제에 실패하였습니다.";
      
      Map<String, String> map = new HashMap<>();
       map.put("msg", msg);
         
       return map;
   }
   
   
}
