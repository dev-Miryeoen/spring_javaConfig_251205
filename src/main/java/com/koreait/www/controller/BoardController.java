package com.koreait.www.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.koreait.www.domain.BoardVO;
import com.koreait.www.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/board/*")
@Slf4j
@Controller
public class BoardController {
	
	// 생성자 주입
	private final BoardService bsv;
	
	// /board/register => /board/register.jsp
	// 요청경로와 jsp의 경로가 일치하면 void
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/insert")
	public String insert(BoardVO board) {
		log.info(">>> insert board >> {}", board);
		int isOk = bsv.insert(board);
		log.info(">>> insert isOk >> {}", (isOk > 0)?"ok":"fail");
		
		return "redirect:/";
	}
}
