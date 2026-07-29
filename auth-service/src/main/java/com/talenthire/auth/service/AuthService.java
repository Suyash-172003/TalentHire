package com.talenthire.auth.service;

import java.util.List;

import com.talenthire.auth.dto.AuthResponse;
import com.talenthire.auth.dto.CandidateDetailsRequest;
import com.talenthire.auth.dto.CandidateDetailsResponse;
import com.talenthire.auth.dto.LoginRequest;
import com.talenthire.auth.dto.RegisterRequest;
import com.talenthire.auth.dto.RegisterResponse;

public interface AuthService {

	RegisterResponse register(RegisterRequest request);

	AuthResponse login(LoginRequest request);

	List<CandidateDetailsResponse> getCandidateDetails(CandidateDetailsRequest request);

}
