package com.talenthire.assessment.service;

import com.talenthire.assessment.dto.CodeExecutionRequest;
import com.talenthire.assessment.dto.CodeExecutionResponse;
import com.talenthire.assessment.dto.CreateAssessmentRequest;
import com.talenthire.assessment.dto.CreateAssessmentResponse;
import com.talenthire.assessment.dto.CreateCodingQuestionRequest;
import com.talenthire.assessment.dto.CreateCodingQuestionResponse;
import com.talenthire.assessment.dto.CreateTestCaseRequest;

public interface AssessmentService {

	public CodeExecutionResponse execute(CodeExecutionRequest request);

	public CreateAssessmentResponse createAssessment(CreateAssessmentRequest request, Integer recruiterId);

	public CreateCodingQuestionResponse addCodingQuestion(Integer assessmentId, Integer recruiterId, CreateCodingQuestionRequest request);

	public String addTestCase(Integer codingQuestionId, Integer recruiterId, CreateTestCaseRequest request);

	
}
