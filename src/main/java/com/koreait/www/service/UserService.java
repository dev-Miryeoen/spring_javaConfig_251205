package com.koreait.www.service;

import java.util.List;

import com.koreait.www.domain.UserVO;

public interface UserService {

	int insert(UserVO user);

	int modifyPwdEmpty(UserVO user);

	int modify(UserVO user);

	int delete(String email);

	List<UserVO> getList();

}
