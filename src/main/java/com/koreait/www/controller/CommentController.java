package com.koreait.www.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.koreait.www.domain.CommentVO;
import com.koreait.www.domain.PagingVO;
import com.koreait.www.handler.PagingHandler;
import com.koreait.www.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/comment/*")
@Slf4j
@RestController
public class CommentController {
	private final CommentService csv;
	
	// ResponseEntity<T> : T => respponse 객체의 body에 보낼 값의 타입
	// consumes : 들어오는 값의 데이터 타입 기재
	// produces : 나가는 값의 데이터 타입 기재
	
	@PostMapping(value="/post", consumes = MediaType.APPLICATION_JSON_VALUE,
								produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> post(@RequestBody CommentVO comment) {
		log.info(">>> comment post >> {}", comment);
		// DB로 전달
		int isOk = csv.post(comment);
		return isOk > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) : new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 더보기 버튼 없는 경우
//	@GetMapping(value="/list/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<CommentVO>> list(@PathVariable("bno") long bno){
//		List<CommentVO> list = csv.getList(bno);
//		return new ResponseEntity<List<CommentVO>>(list, HttpStatus.OK);
//	}
	
	// 더보기 버튼 있는 경우
	@GetMapping(value = "/list/{bno}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagingHandler> list(
			@PathVariable("bno") long bno, @PathVariable("page") int page){
		PagingVO pgvo = new PagingVO(page,5);
//		List<CommentVO> list = csv.getList(bno, pgvo);
//		int totalCount = csv.getTotal(pgvo);
//		PagingHandler ph = new PagingHandler(totalCount, pgvo, list);
		PagingHandler ph = csv.getList(bno, pgvo);		
		return new ResponseEntity<PagingHandler>(ph, HttpStatus.OK);
	}
	
	// responseBody만 보내기
	@ResponseBody
	@PutMapping("/modify")
	public String modify(@RequestBody CommentVO comment) {
		int isOk = csv.update(comment);
		return isOk > 0 ? "1":"0";
	}
	
//	@PutMapping("/modify")
//	public ResponseEntity<String> modify2(@RequestBody CommentVO comment){
//		int isOk = csv.update(comment);
//		return isOk > 0 ? new ResponseEntity<String>("1", HttpStatus.OK):
//			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
//	}
	
	@ResponseBody
	@DeleteMapping("/delete/{cno}/{bno}")
	public String delete(@PathVariable("cno") long cno, @PathVariable("bno") long bno) {
		int isOk = csv.delete(cno, bno);
		return isOk > 0 ? "1":"0";
	}
}
