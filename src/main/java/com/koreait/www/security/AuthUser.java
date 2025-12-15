package com.koreait.www.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.koreait.www.domain.UserVO;

import lombok.Getter;

public class AuthUser extends User {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private UserVO userVO;
	
	public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public AuthUser(UserVO userVO) {
		super(userVO.getEmail(), userVO.getPwd(), 
				userVO.getAuthList().stream()
				.map(authVO -> new SimpleGrantedAuthority(authVO.getAuth()))
				.collect(Collectors.toList())
			);
		this.userVO = userVO;
	}

}
