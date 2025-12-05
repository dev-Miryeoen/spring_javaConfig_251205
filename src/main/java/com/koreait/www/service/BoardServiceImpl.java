package com.koreait.www.service;

import org.springframework.stereotype.Service;

import com.koreait.www.domain.BoardVO;
import com.koreait.www.repository.BoardDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService{
	
	private final BoardDAO bdao;

	@Override
	public int insert(BoardVO board) {
		// TODO Auto-generated method stub
		return bdao.insert(board);
	}
}
