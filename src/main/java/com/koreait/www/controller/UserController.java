package com.koreait.www.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.www.domain.UserVO;
import com.koreait.www.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/*")
@Controller
public class UserController {
	
	private final UserService usv;
	private final BCryptPasswordEncoder bcEncoder;
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	@PostMapping("/insert")
	public String insert(UserVO user) {
		// 회원가입 => password 암호화
		log.info(">>> join user {}", user);
		user.setPwd(bcEncoder.encode(user.getPwd()));
		log.info(">>> join encodePW user {}", user);
		int isOk = usv.insert(user);
		return "redirect:/user/login";
	}
	
	@GetMapping("/login")
	public void login() {
		
	}
	
	@PostMapping("/login")
	public String loginPost(HttpServletRequest request, RedirectAttributes re) {
		// 로그인 실패시 에러메세지 전송을 위해 사용
		
		// 실제 로그인시 Security의 filter에서 가져감. ==> 컨트롤러를 안탐.
		// 로그인 실패시 다시 로그인 페이지로 돌아와 오류메시지 전송
		log.info(">>> failEmail >> {}", request.getAttribute("failEmail"));
		log.info(">>> errMsg >> {}", request.getAttribute("errMsg"));
		
		re.addFlashAttribute("failEmail", request.getAttribute("failEmail"));
		re.addFlashAttribute("errMsg", request.getAttribute("errMsg"));
		
		return "redirect:/user/login";
	}
	
	@GetMapping("/list")
	public void list(Model model) {
		List<UserVO> userList = usv.getList();
		model.addAttribute("userList", userList);
	}
	
	@GetMapping("/modify")
	public String modify() {
		// user의 객체를 DB에서 가져와서(email) modify.jsp로 전송
		// principal 객체를 이용해도 됨.
		return "/user/modify";
	}
	
	@PostMapping("/modify")
	public String modify(UserVO user, RedirectAttributes re, HttpServletRequest request, HttpServletResponse response) {
		int isOk = 0;
		if(user.getPwd().isEmpty() || user.getPwd().length() == 0) {
			// pwd가 공백일 경우 nickName만 수정
			isOk = usv.modifyPwdEmpty(user);
		}else {
			// pwd가 있다면 암호화하여 저장
			user.setPwd(bcEncoder.encode(user.getPwd()));
			isOk = usv.modify(user);
		}
		// 회원 정보 수정 완료 후 => 로그아웃 => 재 로그인 (변경된 환경을 다시 셋팅)
		// / 경로로 다시 접속
		
		if(isOk > 0) {
			logout(request, response);
			re.addFlashAttribute("modify_msg", "ok");
			return "redirect:/";
		} else {
			re.addFlashAttribute("modify_msg", "fail");
			return "redirect:/user/modify";			
		}
	}
	
	@GetMapping("/remove")
	public String remove(RedirectAttributes re, Principal principal, HttpServletRequest request, HttpServletResponse response) {
		String email = principal.getName(); // username
		int isOk = usv.delete(email);
		
		logout(request,response);
		
		String msg = (isOk > 0) ? "ok":"fail";
		re.addFlashAttribute("remove_msg", msg);
		
		return "redirect:/";
	}
	
	
	// logout을 하는 메서드 구현
	private void logout(HttpServletRequest request, HttpServletResponse response) {
		// 내가 로그인 할 때 사용한 시큐리티의 authentication 객체
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		new SecurityContextLogoutHandler().logout(request, response, authentication);
	}
}
