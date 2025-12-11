package com.koreait.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.koreait.www.domain.CommentVO;
import com.koreait.www.domain.PagingVO;

public interface CommentDAO {

	int post(CommentVO comment);

//	List<CommentVO> getList(long bno);
	
	int getTotal(long bno);

	List<CommentVO> getList(@Param("bno") long bno, @Param("pgvo") PagingVO pgvo);

	int update(CommentVO comment);

	int delete(long cno);

}
