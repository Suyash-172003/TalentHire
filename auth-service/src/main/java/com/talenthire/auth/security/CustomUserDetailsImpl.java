package com.talenthire.auth.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.talenthire.auth.entity.User;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
public class CustomUserDetailsImpl implements UserDetails {
	
	private final User user;
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(user.getUserRole().name()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}
	
	public User getUser()
	{
		return user;
	}

}
