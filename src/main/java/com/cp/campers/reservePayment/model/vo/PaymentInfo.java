package com.cp.campers.reservePayment.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfo {
	
	private int payNo;
	private int payAmount;
	private Date payDate;
	private String payKind;
	private int reserNo;
}
