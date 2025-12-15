package com.koreait.www.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.koreait.www.repository.UserDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	// lastLogin기록 => 로그인 후 가야할 경로 설정
	// 직전 경로로 돌아가기
	
	@Autowired
	private UserDAO udao;
	
	// redirect 경로로 이동하는 역할
	private RedirectStrategy redStr = new DefaultRedirectStrategy();
	
	// 직전 경로의 url 정보를 가지고 있는 객체 (세션의 캐쉬 정보)
	private RequestCache reqCache = new HttpSessionRequestCache();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// lastLogin기록 => email (username) 필요
		// authentication => getName() => return username
		String authEmail = authentication.getName();
		
		int isOk = udao.updateLastLogin(authEmail);
		
		log.info(">>> authentication >> {}", authentication.toString());
		
		// 직전 url이 없을 경우 가야하는 경로
		String authUrl = "/board/list";
		
		// 시큐리티에서 로그인을 시도하면 로그인 실패시 기록이 남게 됨.
		// 로그인을 성공하면 기존 실패했던 기록을 삭제
		HttpSession ses = request.getSession();
		if(ses == null) {
			return;
		}else {
			// 기존 실패했던 기록을 삭제
			ses.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}

		// 로그인 직전 URL로 연결
		SavedRequest saveRequest = reqCache.getRequest(request, response);
		redStr.sendRedirect(request, response,
				saveRequest != null ? saveRequest.getRedirectUrl() : authUrl
				);
		
	}

}
