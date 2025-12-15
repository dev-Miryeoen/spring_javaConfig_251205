package com.koreait.www.repository;

import java.util.List;

import com.koreait.www.domain.AuthVO;
import com.koreait.www.domain.UserVO;

public interface UserDAO {

	int insert(UserVO user);

	int insertAuth(String email);

	UserVO getUser(String username);

	List<AuthVO> getAuthList(String username);

	int updateLastLogin(String authEmail);

	int modifyPwdEmpty(UserVO user);

	int modify(UserVO user);

	int delete(String email);

	List<UserVO> getList();

	int authDelete(String email);

}
