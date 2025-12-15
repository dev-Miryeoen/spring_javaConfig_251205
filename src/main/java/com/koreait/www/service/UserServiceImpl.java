package com.koreait.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koreait.www.domain.UserVO;
import com.koreait.www.repository.UserDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService{

	private final UserDAO udao;

	@Transactional
	@Override
	public int insert(UserVO user) {
		// user 객체와 / auth 객체 2개를 insert
		int isOk = udao.insert(user);
		if(isOk > 0) {
			isOk *= udao.insertAuth(user.getEmail());
		}
		return isOk;
	}

	@Override
	public int modifyPwdEmpty(UserVO user) {
		// TODO Auto-generated method stub
		return udao.modifyPwdEmpty(user);
	}

	@Override
	public int modify(UserVO user) {
		// TODO Auto-generated method stub
		return udao.modify(user);
	}

	@Transactional
	@Override
	public int delete(String email) {
		// 삭제시 권한도 같이 삭제
		int isOk = udao.delete(email);
		isOk *= udao.authDelete(email);
		return isOk;
	}

	@Transactional
	@Override
	public List<UserVO> getList() {
		// UserVO => authList => 두개의 값을 가져와야 함.
		List<UserVO> userList = udao.getList();
		for(UserVO u : userList) {
			u.setAuthList(udao.getAuthList(u.getEmail()));
		}
		return userList;
	}
}
