package com.cp.campers.reservePayment.model.vo;


import com.cp.campers.mypage.model.vo.Camp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveInfo {

   private int reserNo;          // 예약번호 
   private int userNo;           // 회원번호
   private String reserName;       // 예약자명
   private String reserState;      // 예약상태
   private String phone;          // 핸드폰번호
   private String request;         // 예약요청사항
   private String checkIn;       // 체크인
   private String checkOut;       // 체크아웃
   private int deadLine;         // 리뷰기한
   private String accountNum;     // 환불계좌번호
   private String accountHol;      // 예금주
   private String bank;           // 은행
   private int roomQua;          // 예약객실수량
   private int roomNo;             // 객실번호
   private String roomName;      // 객실이름
   
   private Camp camp;            // 예약 숙소 정보
   private String writeOrNot;      // 리뷰 작성여부
}