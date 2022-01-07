package com.cp.campers.mypage.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cp.campers.member.model.vo.Member;
import com.cp.campers.mypage.model.vo.Camp;

public interface MypageService{

	/* 회원 목록 */
	List<Member> findAllMember();
	
	/* 캠핑장 등록 */
	void mypageCampEnrollment(Camp camp);

	/* 회원정보 수정 */
	void changeInfoModify(Member member/*, String email, String phone, String nickName*/);

	/* 비밀번호 변경 */
	void changeInfoPwdModify(Member member, String pwd);

	/* 닉네임 체크 */
	int nickNameCheck(String nickName);

	// 회원목록
	//Map<String, Object> findAllMember();
}
