package com.cp.campers.reservePayment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cp.campers.reservePayment.model.ReserPayService;

@Controller
@RequestMapping("/reserPay")
public class ReserPayController {

	ReserPayService reserPayService;
	
	@Autowired
	public ReserPayController(ReserPayService reserPayService) {
		this.reserPayService = reserPayService;
	}
	
	
//	@GetMapping("/reserPay")
//	public String reserPay() {
//		return "reservePayment/reserPay";
//	} 
	
	public String reserPay() {
		return "reservePayment/reserPay";
	}
	
	
	@PostMapping("/insert")
	public String insertReserPay(//) {
		//reserPayService.insertReserPay();
		
		return null;
	}
}
