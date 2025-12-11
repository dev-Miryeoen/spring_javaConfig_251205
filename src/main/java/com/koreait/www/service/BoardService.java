package com.koreait.www.service;

import java.util.List;

import com.koreait.www.domain.BoardFileDTO;
import com.koreait.www.domain.BoardVO;
import com.koreait.www.domain.FileVO;
import com.koreait.www.domain.PagingVO;

public interface BoardService {

//	int insert(BoardVO board);
	int insert(BoardFileDTO bfdto);

	List<BoardVO> getList(PagingVO pgvo);

	BoardFileDTO getDetail(long bno);

	int update(BoardFileDTO boardFileDTO);

	int delete(long bno);

	int getTotalCount(PagingVO pgvo);

	int readCountUp(long bno, int i);

	int removeFile(String uuid);

	FileVO getFile(String uuid);


}
