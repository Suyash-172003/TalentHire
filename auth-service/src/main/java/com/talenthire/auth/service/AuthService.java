package com.talenthire.auth.service;

import java.util.List;

import com.talenthire.auth.dto.AuthResponse;
import com.talenthire.auth.dto.CandidateDetailsRequest;
import com.talenthire.auth.dto.CandidateDetailsResponse;
import com.talenthire.auth.dto.ForgotPasswordRequest;
import com.talenthire.auth.dto.LoginRequest;
import com.talenthire.auth.dto.RegisterRequest;
import com.talenthire.auth.dto.RegisterResponse;
import com.talenthire.auth.dto.ResetPasswordRequest;
import com.talenthire.auth.dto.VerifyOtpRequest;

public interface AuthService {

	RegisterResponse register(RegisterRequest request);

	AuthResponse login(LoginRequest request);

	List<CandidateDetailsResponse> getCandidateDetails(CandidateDetailsRequest request);

	void forgotPassword(ForgotPasswordRequest request);

	void verifyOtp(VerifyOtpRequest request);

	void resetPassword(ResetPasswordRequest request);

}
