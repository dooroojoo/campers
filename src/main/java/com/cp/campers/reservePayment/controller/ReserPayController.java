package com.cp.campers.reservePayment.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cp.campers.reservePayment.model.service.ReserPayService;
import com.cp.campers.reservePayment.model.vo.ReserveInfo;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/reserPay")
@Slf4j
public class ReserPayController {

	private ReserPayService reserPayService;
	private MessageSource messageSource;
	
	@Autowired
	public ReserPayController(ReserPayService reserPayService, MessageSource messageSource) {
		this.reserPayService = reserPayService;
		this.messageSource = messageSource;
	}
	
	
//	@GetMapping("/reserPay")
//	public String reserPay() {
//		return "reservePayment/reserPay";
//	} 
	
	public String reserPay() {
		return "reservePayment/reserPay";
	}
	
	
	@PostMapping("/insert")
	public String insertReserPay(@ModelAttribute ReserveInfo reserveInfo, RedirectAttributes rttr,Locale locale) {
		
		log.info("예약 정보 : {}", reserveInfo);
		
		int result = reserPayService.insertReserPay(reserveInfo);
		
		if(result > 0 ) {
			rttr.addFlashAttribute("successMessage", messageSource.getMessage("insertReserPay", null, locale));
		} else {
			log.info("예약 정보 insert 후 result 값 : {}",  result);
		}
		
		// 태스트로 메인 페이지 이동
		return "redirect:main";
	}
}
