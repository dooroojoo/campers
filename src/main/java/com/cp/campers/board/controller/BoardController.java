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
import com.cp.campers.board.model.vo.Comment;
import com.cp.campers.board.model.vo.Search;
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
	public void boardList(Model model) {
		
		int page = 1;
		
		Map<String, Object> map = boardService.selectBoardList(page);
		
		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("pi",map.get("pi"));
		model.addAttribute("thumbnailList", map.get("thumbnailList"));
	}
	
	@GetMapping("/listPage")
	public String boardListPage(Model model, @RequestParam int page) {
		
		Map<String, Object> map = boardService.selectBoardList(page);
		
		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("pi",map.get("pi"));
		model.addAttribute("thumbnailList", map.get("thumbnailList"));
		
		return "board/list";
	}
	
	@GetMapping("/list/search")
	public String searchBoard(Search search, Model model) {

		int page = 1;
		
		Map<String, Object> map = boardService.searchBoardList(page, search);

		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("pi", map.get("pi"));
		model.addAttribute("thumbnailList", map.get("thumbnailList"));
		
		return "board/list";
	}
	@GetMapping("/list/searchPage")
	public String searchBoardPage(Search search, Model model, int page) {
		
		Map<String, Object> map = boardService.searchBoardList(page, search);

		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("pi", map.get("pi"));
		model.addAttribute("thumbnailList", map.get("thumbnailList"));
		
		return "board/list";
	}

	@GetMapping("/detail")
	public void detailList(Model model, int bid) {
		
		Board board = boardService.boardDetail(bid);
		
		model.addAttribute(board);
		
		List<Comment> commentList = boardService.selectCommentList(bid);
		
		List<Comment> refCommentList = boardService.selectRefCommentList(bid);
		
		model.addAttribute(commentList);
		model.addAttribute(refCommentList);
		
		log.info("refComm"+refCommentList);
		
	}
	@PostMapping("/comment")
	public String commentWrite(Model model,int bid,String reply, Comment comment, @AuthenticationPrincipal UserImpl loginUser) {
		comment.setBid(bid);
		comment.setCWriter(loginUser.getUserNo());
		comment.setCContent(reply);
		
		boardService.insertComment(comment);
		
		return "redirect:/board/detail?bid="+bid;
	}
	@PostMapping("/refcomment")
	public String refcommentWrite(Model model, Comment comment, @AuthenticationPrincipal UserImpl loginUser ) {
		log.info("refWriter = "+comment.getRefWriter());
		log.info("bid = "+comment.getBid());
		log.info("comment="+comment.getCContent());
		
		comment.setCWriter(loginUser.getUserNo());
		
		boardService.insertRefComment(comment);
		
		
		return "redirect:/board/detail?bid="+comment.getBid();
	}

	@GetMapping("/write")
	public void writeBoard() {
	}

	@GetMapping("/mycomment")
	public void myCommentList() {
	}

	@GetMapping("/myboard")
	public void myBoardList() {
	}

	@PostMapping("/write")
	public String insertBoard(@Value("${custom.path.upload-images}") String uploadFilesPath, Model model,
			@RequestParam MultipartFile[] images, HttpServletRequest request, Board board,
			@AuthenticationPrincipal UserImpl loginUser) {

		board.setWriter(loginUser.getUserNo());

		String filePath = uploadFilesPath + "/boardImg";

		File mkdir = new File(filePath);
		if (!mkdir.exists())
			mkdir.mkdir();

		List<Map<String, String>> files = new ArrayList<>();

		List<MultipartFile> imageList = new ArrayList<>();

		for (int i = 0; i < images.length; i++) {

			if (images[i].getSize() != 0) {
				imageList.add(images[i]);
			}
		}

		for (int i = 0; i < imageList.size(); i++) {

			String originFileName = imageList.get(i).getOriginalFilename();
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
			for (int i = 0; i < imageList.size(); i++) {

				Map<String, String> file = files.get(i);
				imageList.get(i).transferTo(new File(file.get("filePath") + "\\" + file.get("savedName")));

				Attachment attachment = new Attachment();
				attachment.setFileName(file.get("savedName"));
				attachment.setFileRoute("/resources/images/uploadFiles/boardImg/");

				if (i == 0)
					attachment.setFileLevel(0);
				else
					attachment.setFileLevel(1);

				boardService.insertBoardImage(attachment);

			}

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			for (int i = 0; i < imageList.size(); i++) {

				Map<String, String> file = files.get(i);
				new File(file.get("filePath") + "\\" + file.get("savedName")).delete();

			}
		}

		return "redirect:/board/list";
	}

	
}
