package com.cp.campers.mypage.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cp.campers.board.model.vo.Attachment;
import com.cp.campers.board.model.vo.Board;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.mypage.model.vo.Camp;

public interface MypageService{

	/* 회원 목록 */
	List<Member> findAllMember();
	
	List<Board> findAllBoard();
		
	/* 회원정보 수정 */
	Member changeInfoModify(Member member/*, String email, String phone, String nickName*/);

	/* 비밀번호 변경 */
	void changeInfoPwdModify(Member member);

	/* 닉네임 체크 */
	int nickNameCheck(String nickName);

	/* 회원 탈퇴 */
	void changeInfoMemberout(Member member);

	/* 내 게시판 목록 */
	Map<String, Object> selectMyBoardList(int writer, int page);

	/* 내 정보 목록 */
	Map<String, Object> selectMyMemberList(int userNo, int page);

	/* 숙소 등록 */
	void mypage_camp_enrollment_room(Camp camp);

	/* 캠프장 사진 등록 */
	void insertCampImage(Attachment attachment);

	/* 숙소 사진 등록 */
	void insertRoomImage(Attachment atta2);

	int selectCampNo();

	Map<String, Object> selectMyPageList(int writer);

	void mypageCampEnrollment(Camp camp, Attachment attachment, Attachment atta2);

	/* 사업자 예약내역 확인 */
	Map<String, Object> selectMyHostReserveList(int writer, int page);

	
}
