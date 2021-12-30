package com.cp.campers.member.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp.campers.member.model.dao.MemberMapper;
import com.cp.campers.member.model.vo.Authority;
import com.cp.campers.member.model.vo.Member;
import com.cp.campers.member.model.vo.MemberRole;
import com.cp.campers.member.model.vo.UserImpl;

@Service
public class MemberServiceImpl implements MemberService{

	private MemberMapper memberMapper;
	
	@Autowired
	public MemberServiceImpl(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Member member = memberMapper.findMemberById(username);
		
		if(member == null) member = new Member(); 
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		if(member.getMemberRoleList() != null) {
			List<MemberRole> roleList = member.getMemberRoleList();
			
			for(MemberRole role : roleList) {
				Authority authority = role.getAuthority();
				
				if(authority != null) {
					authorities.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
					
				}
			}
		}
		
		UserImpl user = new UserImpl(member.getId(), member.getPwd(), authorities);
		user.setDetails(member);
		
		return user;
	}

	@Transactional
	@Override
	public void signUp(Member member) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setPwd(passwordEncoder.encode(member.getPwd()));
		
		memberMapper.insertMember(member);
		
		MemberRole memberRole = new MemberRole();
		memberRole.setAuthorityCode(1);
		memberMapper.insertMemberRole(memberRole);
		
	}

}
