package com.cp.campers.mypage.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cp.campers.board.model.vo.Board;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.mypage.model.vo.Camp;

public interface MypageService{

	/* 회원 목록 */
	List<Member> findAllMember();
	
	List<Board> findAllBoard();
	
	/* 캠핑장 등록 */
	void mypageCampEnrollment(Camp camp);

	/* 회원정보 수정 */
	int changeInfoModify(Member member/*, String email, String phone, String nickName*/);

	/* 비밀번호 변경 */
	void changeInfoPwdModify(Member member);

	/* 닉네임 체크 */
	int nickNameCheck(String nickName);

	/* 회원 탈퇴 */
	void changeInfoMemberout(Member member);

	/* 게시판 목록*/
	Map<String, Object> selectMyBoardList(int writer, int page);

	/* 숙소 등록 */
	void mypage_camp_enrollment_room(Camp camp);

	// 회원목록
	//Map<String, Object> findAllMember();
}
