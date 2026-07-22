package com.talenthire.assessment.service;

import com.talenthire.assessment.dto.CodeExecutionRequest;
import com.talenthire.assessment.dto.CodeExecutionResponse;

public interface AssessmentService {

	public CodeExecutionResponse execute(CodeExecutionRequest request);

	
}
