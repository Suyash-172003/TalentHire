package com.talenthire.assessment.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.talenthire.assessment.dto.CandidateDetailsRequest;
import com.talenthire.assessment.dto.CandidateDetailsResponse;

@FeignClient(name="auth-service")
public interface AuthClient {
	
	  @PostMapping("/auth/users/details")
	    List<CandidateDetailsResponse> getCandidateDetails(
	            @RequestBody CandidateDetailsRequest request);



}
