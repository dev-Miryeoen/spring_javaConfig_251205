package com.koreait.www.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CommonExceptionAdvice {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handler404(NoHandlerFoundException ex) {
		log.info(">>> exception >> {}", ex.getMessage());
		return "custom404";
	}
	
	// 404외 다른에러 통틀어 처리
//	@ExceptionHandler(Exception.class)
//	public String exception(Exception ex) {
//		return "새로 만들어서 연결";
//	}
}
