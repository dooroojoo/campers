package com.cp.campers.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.cp.campers.member.model.service.MemberService;
import com.cp.campers.member.model.vo.UserImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthSuccessureHandler implements AuthenticationSuccessHandler {
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
	private MemberService memberService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		UserImpl principal = (UserImpl)authentication.getPrincipal();
		log.info(principal+"");
		
		if(principal.getStatus().equals("Y")) {
			memberService.resetFailCount(principal.getUserNo());
		}else if(principal.getStatus().equals("N")) {
			throw new DisabledException("exceptionNo3");
		}else if(principal.getStatus().equals("X")) {
			throw new DisabledException("exceptionNo3And2");
		}else if(principal.getStatus().equals("L")) {
			throw new LockedException("");
		}
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		// 인증이 필요한 리소스에 접근하려다 로그인 화면으로 넘어간경우
		if (savedRequest != null) {
			redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
		} else { // 직접 로그인 페이지로 이동해서 들어온경우 메인페이지로 리다이렉트
			redirectStrategy.sendRedirect(request, response, "/");
		}

	}

}
