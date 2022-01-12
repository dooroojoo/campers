package com.cp.campers.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cp.campers.camp.model.vo.Room;
import com.cp.campers.admin.model.vo.CalculateInfo;
import com.cp.campers.admin.model.vo.PageInfo;
import com.cp.campers.admin.model.vo.Report;
import com.cp.campers.admin.model.vo.Search;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.member.model.vo.MemberRole;
import com.cp.campers.camp.model.vo.Camp;

@Mapper
public interface AdminMapper {

	// 회원수
	int getListCount();

	// 검색 회원수
	int getListCountBySearch(Search search);

	// 회원목록
	List<Member> findAllMember(PageInfo pi);

	// 회원활동 수정
	int updateMember(Member member);

	// 회원권한 수정
	int updateMemberRole(MemberRole mr);

	// 회원검색
	List<Member> searchMember(Map<String, Object> param);

	// 신고등록
	int insertReport(Report report);
	
	// 게시물신고
	int insertReportByBoard(int id);
	
	// 댓글신고
	int insertReportByReply(int id);
	
	// 신고수
	int getReportCount();
	
	// 신고목록
	List<Report> findAllReport(PageInfo pi);
	
	// 신고상태변경
	int completeReport(int rNo);
	
	// 숙소개수
	int getCampListCount();

	// 숙소목록
	List<Camp> findAllCamp(PageInfo pi);

	// 검색 숙소개수
	int getCampListCountBySearch(Search search);

	// 숙소검색
	List<Camp> searchCamp(Map<String, Object> param);

	// 숙소상세
	Camp detailCamp(int campNo);
	
	// 객실상세
	List<Room> detailRoom(int campNo);
	
	// 숙소삭제
	int deleteCamp(int campNo);

	// 숙소거절
	void refusal(Map<String, Object> param);

	// 숙소등록
	void enroll(int campNo);

	// 등록이력	
	void recordToEnroll(int campNo);
	
	// 신규이력
	void recordToNew(int campNo);
	
	// 해지이력
	void recordToDelete(int campNo);
	
	// 정산관리 목록 개수 
	int getcalculateListCount();

	// 정산관리목록
	List<CalculateInfo> calculateList(PageInfo pi);
	
	


}
