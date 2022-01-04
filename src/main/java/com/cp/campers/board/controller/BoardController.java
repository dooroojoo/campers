package com.cp.campers.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cp.campers.board.model.service.BoardService;
import com.cp.campers.board.model.vo.Attachment;
import com.cp.campers.board.model.vo.Board;
import com.cp.campers.member.model.vo.UserImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
	
	private BoardService boardService;
	
	 @Autowired
	   public BoardController(BoardService boardService) {
	      this.boardService = boardService;
	 }
	 
	
	@GetMapping("/list")
	public void boardList() {}
	
	@GetMapping("/detail")
	public void detailList() {}
	
	@GetMapping("/write")
	public void writeBoard() {}
	
	@GetMapping("/mycomment")
	public void myCommentList() {}
	
	@GetMapping("/myboard")
	public void myBoardList() {}
	
	@PostMapping("/write")
	public String insertBoard(@Value("${custom.path.upload-images}") String uploadFilesPath, Model model, @RequestParam List<MultipartFile> images
			, HttpServletRequest request, Board board, @AuthenticationPrincipal UserImpl loginUser) {
		
		  log.info(""+loginUser.getUserNo()+"");
		  log.info(board.getTitle());
		  log.info(board.getContent());
		  log.info("uploadFilesPath="+uploadFilesPath);
		  
		  board.setWriter(loginUser.getUserNo());
		  
		  String filePath = uploadFilesPath + "/boardImg";
		  
		  File mkdir = new File(filePath);
		  if(!mkdir.exists()) mkdir.mkdir();
		  
		  List<Map<String,String>> files = new ArrayList<>();
		  
		  int imagesize = 0;
				  
		  for(int i = 0 ; i < images.size(); i++) {
			  if(images.get(i).getSize()!= 0) {
				  imagesize += 1;
				  
			  }
		  }
		  
		  log.info("imagesize="+imagesize);
		  
		  for(int i = 0 ; i < imagesize; i++) {
			 
			  log.info("images="+images.get(i).getOriginalFilename()+"");
				  
			  String originFileName = images.get(i).getOriginalFilename();
			  String ext = originFileName.substring(originFileName.lastIndexOf("."));
			  String savedName = UUID.randomUUID().toString().replace("-", "") + ext;
			  
			  Map<String, String> file = new HashMap<>();
			  file.put("originFileName", originFileName);
			  file.put("savedName", savedName);
	    	  file.put("filePath", filePath);
	    	  files.add(file);
		  }
		  
		 try {
			 
			 boardService.insertBoard(board);
		  for(int i = 0 ; i < imagesize; i++) {
			  Map<String,String> file = files.get(i);
				images.get(i).transferTo(new File(file.get("filePath")+"\\"+file.get("savedName")));
				
				Attachment attachment = new Attachment();
				attachment.setFileName(file.get("savedName"));
				attachment.setFileRoute("/boardImg");
				
				if(i==0)
					attachment.setFileLevel(0);
				else
					attachment.setFileLevel(1);
				
				boardService.insertBoardImage(attachment);
				
		  	}
		  
		  	log.info("파일업로드 성공");
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		  for(int i = 0 ; i <imagesize; i++) {
			  Map<String, String> file = files.get(i);
			  new File(file.get("filePath")+"\\"+file.get("savedName")).delete();
		  }
		  log.info("파일업로드 실패");
		  }
		  
		  
	
		return "board/list";
	}
	
}
