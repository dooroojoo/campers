package com.cp.campers.mypage.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cp.campers.board.model.vo.Attachment;
import com.cp.campers.board.model.vo.Board;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.mypage.model.vo.BusinessReservation;
import com.cp.campers.mypage.model.vo.BusinessType;
import com.cp.campers.mypage.model.vo.Camp;
import com.cp.campers.mypage.model.vo.ImageFile;
import com.cp.campers.mypage.model.vo.Room;
import com.cp.campers.mypage.model.vo.RoomFile;
import com.cp.campers.mypage.model.vo.WishCamp;
import com.cp.campers.reservePayment.model.vo.ReserveInfo;

@Mapper
public interface MypageMapper {
	


	// 회원 목록
	List<Member> findAllMember();
	
	// 게시물 조회
	List<Board> findAllBoard();
	
	// 찜한 회원 목록  
	List<WishCamp> findAllWishCamp();
		
	/* 캠핑장 등록 */
	void insertCamp(Camp camp);

	/* 캠핑장 타입 등록 */
	void insertBusinessType(BusinessType businessType);

	// 회원수
	int getListCount();	

	// 사업장 타입 등록
	// Integer 체크박스 담기
	//void insertCampBusinessType(Integer businessNo);
	void insertCampBusinessType(String btype);
	
	
	// 사업장 시설 등록
	// Integer 체크박스 담기
	//void insertCampFacility(Integer facilityNo);
	void insertCampFacility(String ftype);
	

	// 캠프장 등록시 객실 등록
	void insertRoom(Room room);
	
	// 객실만 등록
	void insertRoom2(Room room);
		
	// 캠핑장 사진 파일 등록
	// void insertCampFile(CampFile campFile);
	//void insertCampFile(Integer fileNo);
	
	// 숙소 사진 파일 등록
	void insertRoomFile(RoomFile roomFile);
	
	// 회원정보 수정
	int changeInfoModify(Member member);

	// 비밀번호 변경
	int changeInfoPwdModify(Member member);
	
	// 회원 탈퇴
	int changeInfoMemberout(Member member);

	/* 닉네임 체크 */
	int nickNameCheck(String nickName);

	List<Board> selectMyBoardList(Map<String, Object> param);

	List<Board> selectThumbnailList();
	
	List<Camp> selectThumbnailList2();

	int getListCountMyBoard(int writer);

	/* 캠프 사진 */
	void insertCampImage(Attachment attachment);

	/* 숙소 사진 */
	void insertRoomImage(Attachment atta2);

	void insertImageNo();

	void insertImageNo2(int roomNo);

	int selectCampNo();

	int getListCountMyMember(int writer);

	int selectcurrval();

	List<Camp> selectCampList();

	List<Member> selectMyMemberList(Map<String, Object> param);

	/* 회원 총 예약개수 */
	int getCountMyGuestReserveList(int userNo);

	/* 사업자 예약내역 */
	List<Camp> selectMyHostReserveList(Map<String, Object> param);

	List<Camp> selectCampImageList();

	/* 내 캠핑장 찾기 */
	List<Camp> selectMyCampList(Map<String, Object> param);

	/* 사업자 캠핑장 찾기 */
	List<ReserveInfo> selectHostReserveList(Map<String, Object> param);
	
	/* 회원 예약내역 */
	List<ReserveInfo> selectMyGuestReserveList(Map<String, Object> param);

	/* 예약 취소 */
	int reserveCancle(int reserNo);
	
	/* 프로필 사진 변경 */
	void updateProfilePath(Member member);

	void deleteMember(Member member);

	void pwdUpdate(Map<String,String> param);


	void campImageInsert(Attachment attachment);

	List<BusinessReservation> selectBusinessReservationList(int userNo);

	List<ReserveInfo> selectReserveInfoList();


	void campImageNo();

	void roomInsert(Room room);

	void roomImageInsert(Attachment atta2);

	void roomImageNo();

	String selectRoomNoList();

	String selectPwd(Member member);

	
	// public Map<String, Object> findAllMember();
}
