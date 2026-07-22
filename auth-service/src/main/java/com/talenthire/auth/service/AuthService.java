package com.talenthire.auth.service;

import com.talenthire.auth.dto.AuthResponse;
import com.talenthire.auth.dto.LoginRequest;
import com.talenthire.auth.dto.RegisterRequest;
import com.talenthire.auth.dto.RegisterResponse;

public interface AuthService {

	RegisterResponse register(RegisterRequest request);

	AuthResponse login(LoginRequest request);

}
