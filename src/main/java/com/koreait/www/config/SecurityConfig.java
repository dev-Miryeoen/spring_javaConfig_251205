package com.koreait.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.koreait.www.security.CustomAuthUserService;
import com.koreait.www.security.LoginFailureHandler;
import com.koreait.www.security.LoginSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	// 비밀번호 암호화 객체 Bean 생성 PasswordEncoder
	
	@Bean
	public PasswordEncoder bcPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// SuccessHandler 객체 Bean 생성 => 사용자 커스텀 객체
	@Bean
	public AuthenticationSuccessHandler authSuccessHandler() {
		return new LoginSuccessHandler();
	}
	
	// FailureHandler 객체 Bean 생성 => 사용저 커스텀 객체
	@Bean
	public AuthenticationFailureHandler authFailureHandler() {
		return new LoginFailureHandler();
	}
	
	
	// UserDetails 객체 Bean 생성 => 사용자가 생성해야 하는 객체
	@Bean
	public UserDetailsService customDetailsService() {
		return new CustomAuthUserService();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 인증용 객체 생성 매니저
		auth.userDetailsService(customDetailsService())
			.passwordEncoder(bcPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 권한에 따른 주소 맵핑 설정
		// 로그인 로그아웃 구성정보 설정
		
		// csrf 공격에 대한 설정
		// security에서는 기본적으로 활성화 되어있는 기능
		// 클라이언트 -> 서버 요청을 봅낼 때 csrf 토큰을 함께 전송
		// get mapping은 토큰 전송이 불필요 (서버에서 일방적으로 데이터를 보내는 mapping)
		// post, put, delete 토큰 전송 필요
		http.csrf().disable(); // 공격에 대한 방어 시스템 풀기
		
		// 권한에 따른 승인 요청
		// antMatchers : 접근을 허용하는 경로
		// hasRole("권한") : 해당 권한 확인 => ROLE_ 포함 => ROLE_ADMIN
		// authenticated() : 인증된 사용자만 가능한 경로
		// permitAll() : 누구나 접근 가능한 경로
		http.authorizeRequests()
			.antMatchers("/user/list").hasRole("ADMIN")
//			.antMatchers("/",
//						 "/user/login",
//						 "/user/register",
//						 "/board/list",
//						 "/board/detail",
//						 "/resources/**",
//						 "/upload/**",
//						 "/comment/list/**"
//						 ).permitAll()
//			.anyRequest().authenticated();
			.anyRequest().permitAll();
		
		// 로그인 페이지 구성
		// username => email / password => pwd
		http.formLogin()
			.usernameParameter("email")
			.passwordParameter("pwd")
			.loginPage("/user/login") // 로그인 페이지 주소 => controller에 주소요청 경로가 있어야 함.(필수)
//			.defaultSuccessUrl("/") // successHandler 없는 경우
//			.failureUrl("/user/login") // failHandler 없는 경우
			.successHandler(authSuccessHandler())
			.failureHandler(authFailureHandler());
		
		// 로그아웃 구성 => method = "post"
		http.logout()
			.logoutUrl("/user/logout")
			.invalidateHttpSession(true) // 세션끊기
			.deleteCookies("JSESSIONID")
			.logoutSuccessUrl("/");
	}
	
	
}
