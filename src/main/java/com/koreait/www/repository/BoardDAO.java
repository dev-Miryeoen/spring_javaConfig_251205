package com.koreait.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.koreait.www.domain.BoardFileDTO;
import com.koreait.www.domain.BoardVO;
import com.koreait.www.domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO board);

	List<BoardVO> getList(PagingVO pgvo);

	BoardVO getDetail(long bno);

	int update(BoardVO board);

	int delete(long bno);

	int getTotalCount(PagingVO pgvo);
	
    // param 2개 이상 mapper가 인지하게 할 때 @Param
	int readCountUp(@Param("bno") long bno, @Param("i") int i);

	int cmtQtyUpdate(@Param("bno") long bno, @Param("i") int i);

	long getBno();

	int fileQtyUpdate(@Param("bno") long bno, @Param("size") int size);




}
