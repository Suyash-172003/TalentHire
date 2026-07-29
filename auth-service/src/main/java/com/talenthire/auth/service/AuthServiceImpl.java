package com.talenthire.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.talenthire.auth.dto.AuthResponse;
import com.talenthire.auth.dto.CandidateDetailsRequest;
import com.talenthire.auth.dto.CandidateDetailsResponse;
import com.talenthire.auth.dto.LoginRequest;
import com.talenthire.auth.dto.RegisterRequest;
import com.talenthire.auth.dto.RegisterResponse;
import com.talenthire.auth.entity.User;
import com.talenthire.auth.entity.UserRole;
import com.talenthire.auth.exception.UserAlreadyExistsException;
import com.talenthire.auth.repository.UserRepository;
import com.talenthire.auth.security.CustomUserDetailsImpl;
import com.talenthire.auth.utils.JwtUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl  implements AuthService{
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	
	
	

	@Override
	public RegisterResponse register(RegisterRequest request) {
		
boolean isUserExists=userRepository.existsByEmail(request.getEmail());

if(isUserExists)
{
	throw new UserAlreadyExistsException("Email Already Exists");
}

		User user=new User();
		user.setEmail(request.getEmail());
		user.setName(request.getName());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setUserRole(UserRole.valueOf(request.getUserRole().toUpperCase()));
		
		User savedUser=userRepository.save(user);
		
		return new RegisterResponse(savedUser.getUserId(),savedUser.getName(),savedUser.getEmail(),savedUser.getUserRole().name(),"Registration successful");
	}


	public AuthResponse login(LoginRequest request) {
		
//		User user=userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new ResourceNotFoundException("Invalid Credentials"));
//		
//		if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
//		{
//			throw new ResourceNotFoundException("Invalid Credentials");
//		}
		
		UsernamePasswordAuthenticationToken holder = new UsernamePasswordAuthenticationToken(request.getEmail(),
				request.getPassword());
		System.out.println("before " + holder.isAuthenticated());
		
		
		Authentication fullyAuthenticatedDetails=authenticationManager.authenticate(holder);
		
		
		System.out.println("after  " + fullyAuthenticatedDetails.isAuthenticated());
		System.out.println(fullyAuthenticatedDetails.getPrincipal());// custom user details
		CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) fullyAuthenticatedDetails.getPrincipal();
		
		String token=jwtUtils.generateJwt(userDetails);
		
		
		User user=userDetails.getUser();
		

		
		
		
	return new AuthResponse(user.getUserId(),user.getName(),user.getEmail(),user.getUserRole().name(),"Login successful",token);
	}


	@Override
	public List<CandidateDetailsResponse> getCandidateDetails(CandidateDetailsRequest request) {
		 List<User> users =
		            userRepository.findByUserIdIn(request.getUserIds());

		    List<CandidateDetailsResponse> responses =
		            new ArrayList<>();

		    for (User user : users) {

		        CandidateDetailsResponse response =
		                new CandidateDetailsResponse();

		        response.setUserId(user.getUserId());
		        response.setName(user.getName());
		        response.setEmail(user.getEmail());

		        responses.add(response);
		    }

		    return responses;
	}

	

}
