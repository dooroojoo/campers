package com.cp.campers.mypage.model.vo;
 
import java.util.List;

import lombok.Data;

@Data
public class Room {

	private int roomNo;
	private String roomName;
	private int roomMember;
	private int roomPrice;
	private String roomSize;
	private String roomFloor;
	private String roomParking;
	private String roomInfo;
	private int roomAmount;
	private String roomForm;
	private int roomCount;
	private int campNo;
	 
	private List<RoomFileNo> roomFileNoList;
}
