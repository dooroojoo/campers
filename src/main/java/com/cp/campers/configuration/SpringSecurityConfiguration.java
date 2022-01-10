package com.cp.campers.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.cp.campers.member.model.service.MemberService;


/* 스프링 시큐리티 설정 활성화 + bean 등록 가능 */
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private MemberService memberService;
	
	@Autowired
	public SpringSecurityConfiguration(MemberService memberService) {
	      this.memberService = memberService;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
   	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				/* "/mypage/**" 요청은 인증이 되어야 함 */
				.antMatchers("/mypage/**").authenticated()
				/* 로그인 한 회원만 */
				//.antMatchers(HttpMethod.GET, "/mypage/**").hasAnyRole("ADMIN", "MEMBER", "BUISNESS")
				/* 사업자 권한이 있는 사람만 */
				//.antMatchers(HttpMethod.POST, "/mypage/**").hasRole("BUISNESS")
				/* "/admin/**"의 요청은 ROLE_ADMIN 권한을 가진 사람에게만 허용 */
				//.antMatchers("/admin/**").hasRole("ADMIN")
				/* 그 외의 요청은 모두 허가함 - 게스트 사용자도 접근 가능 */
				.anyRequest().permitAll()
			.and()
				.formLogin() //로그인 설정
				.loginPage("/member/login") // 로그인페이지
				.successForwardUrl("/") //로그인 성공후 이동페이지
			.and()
				.logout() //로그아웃 설정
				.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃 요청주소
				.deleteCookies("JSESSIONID") // 쿠키삭제
				.invalidateHttpSession(true) // 세션만료
				.logoutSuccessUrl("/") //로그아웃 성공 후 페이지
			.and()
				.exceptionHandling()
				.accessDeniedPage("/common/denied") // 억지로 접근불가한 경로로 바꿨을때 이동할 페이지	
			.and()
				.csrf().disable();
	}
   
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
	}
   
		
}