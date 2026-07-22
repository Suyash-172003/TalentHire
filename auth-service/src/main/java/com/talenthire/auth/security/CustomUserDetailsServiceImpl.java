package com.talenthire.auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.talenthire.auth.entity.User;
import com.talenthire.auth.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
@Transactional
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	
	private final UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user=userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User by email doesn't exist!!!!!!!!"));
	
		
		
		return new CustomUserDetailsImpl(user);
	}

}
