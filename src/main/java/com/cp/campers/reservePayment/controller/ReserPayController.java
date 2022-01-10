package com.cp.campers.reservePayment.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cp.campers.reservePayment.model.service.ReserPayService;
import com.cp.campers.reservePayment.model.vo.PaymentInfo;
import com.cp.campers.reservePayment.model.vo.ReserveInfo;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class ReserPayController {

	private ReserPayService reserPayService;
	private MessageSource messageSource;
	
	@Autowired
	public ReserPayController(ReserPayService reserPayService, MessageSource messageSource) {
		this.reserPayService = reserPayService;
		this.messageSource = messageSource;
	}
	
	
	@GetMapping("/reserPay")
	public String reserPay() {
		return "reservePayment/reserPay";
	}
	
	/* 예약 및 결제 insert */
	@ResponseBody
	@PostMapping("/reserPay/insert")
	public Map<String, String> insertReserPay(@ModelAttribute ReserveInfo reserveInfo, 
			@ModelAttribute PaymentInfo paymentInfo) {
		
		log.info("예약 정보 : {}", reserveInfo);
		
		String msg = reserPayService.insertReserPay(reserveInfo, paymentInfo) > 0 ? "예약 insert" : "NO";
		
		Map<String, String> map = new HashMap<>();
	    map.put("msg", msg);
	      
	    return map;
	}
	
	@GetMapping("/calculate")
	public String calcurateList(Model model) {
		
		return "admin/calculate";
	}

	
}
