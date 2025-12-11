package com.koreait.www.service;

import java.util.List;

import com.koreait.www.domain.CommentVO;
import com.koreait.www.domain.PagingVO;
import com.koreait.www.handler.PagingHandler;

public interface CommentService {

	int post(CommentVO comment);

//	List<CommentVO> getList(long bno);

	PagingHandler getList(long bno, PagingVO pgvo);

	int update(CommentVO comment);

	int delete(long cno, long bno);

	
}
