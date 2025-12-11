package com.koreait.www.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.koreait.www.domain.BoardVO;
import com.koreait.www.repository.BoardDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.koreait.www.config.RootConfig.class})
public class BoardTest {
	
	@Autowired
	private BoardDAO bdao;
	
	@Test
	public void insertBoardDummies() {
		for(int i=0; i<2000; i++) {
			BoardVO board = new BoardVO();
			board.setTitle("Test title" + (int)(Math.random()*300));
			board.setWriter("tester" + (int)(Math.random()*300));
			board.setContent("Test Content" + i);
			bdao.insert(board);
		}
	}
}
