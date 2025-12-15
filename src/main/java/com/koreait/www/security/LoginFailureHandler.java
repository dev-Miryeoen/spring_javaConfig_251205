package com.koreait.www.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("login failure");
		// 로그인 시도 id
		String failEmail = request.getParameter("email");
		String errorMessage = "";
		
		// BadCredentialsException || InternalAuthenticationServiceException
		
		log.info(">>> failure Exeption >> {}", exception.getMessage().toString());
		if(exception instanceof BadCredentialsException) {
			errorMessage = "아이디/비밀번호가 일치하지 않습니다.";
		}else if(exception instanceof InternalAuthenticationServiceException) {
			errorMessage = "관리자에게 문의하세요.";
		}else {
			errorMessage = "관리자에게 문의하세요.";			
		}
		log.info("errorMessage {}", errorMessage);
		
		request.setAttribute("failEmail", failEmail);
		request.setAttribute("errMsg", errorMessage);
		
		request.getRequestDispatcher("/user/login?error").forward(request, response);
	}

}
