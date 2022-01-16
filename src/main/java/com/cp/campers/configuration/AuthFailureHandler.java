package com.cp.campers.configuration;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.cp.campers.member.model.service.MemberService;
import com.cp.campers.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {
	
	@Autowired
	private MemberService memberService;
	
	private final String FAIL_URL ="/member/login?error=true&exception=";

	@Autowired
	public AuthFailureHandler() {}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String userId = request.getParameter("username");

		String msg = "";
		if (exception instanceof AuthenticationServiceException) {
			msg = "exceptionNo1";
		} else if (exception instanceof BadCredentialsException) {
			int count = loginFailCount(userId);
			msg = "exceptionNo2Count"+count;
		} else if (exception instanceof DisabledException) {
			msg = "exceptionNo3";
		} else if (exception instanceof LockedException) {
			msg = "exceptionNo4";
		}

		request.getRequestDispatcher(FAIL_URL+msg).forward(request, response);

	}
	
	protected int loginFailCount(String username) {
		memberService.loginFailCount(username);
		int count = memberService.checkFailCount(username);
		if(count == 5) {
			memberService.disabledMember(username);
		}
		return count;
	}

}
