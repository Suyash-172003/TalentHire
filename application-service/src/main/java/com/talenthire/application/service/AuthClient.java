package com.talenthire.application.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.talenthire.application.dto.CandidateDetailsRequest;
import com.talenthire.application.dto.CandidateDetailsResponse;

@FeignClient(name="auth-service",url="${feign.client.auth-service.url}")
public interface AuthClient {
	
	  @PostMapping("/auth/users/details")
	    List<CandidateDetailsResponse> getCandidateDetails(
	            @RequestBody CandidateDetailsRequest request);



}
