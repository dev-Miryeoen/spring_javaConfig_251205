package com.koreait.www.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.koreait.www.domain.UserVO;
import com.koreait.www.repository.UserDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthUserService implements UserDetailsService {

	@Autowired
	private UserDAO udao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// username + password => 인증용 토큰 생성
		// username => DB에 가서 username과 일치하는 객체를 User 객체로 생성
		// User 객체 <username, password, auth> => UserDetails 객체로 리턴
		
		// DB에서 해당 username이 일치하는 UserVO 객체 리턴
		UserVO user =  udao.getUser(username);
		
		if(user == null) {
			log.info(">>> username not found! >> {}", username);
			throw new UsernameNotFoundException(username);
		}
		
		user.setAuthList(udao.getAuthList(username));
		log.info(">>> login user >> {}", user);
		
		return new AuthUser(user);
	}

}
