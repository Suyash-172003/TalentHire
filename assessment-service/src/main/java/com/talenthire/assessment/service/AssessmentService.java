package com.talenthire.assessment.service;

import java.util.List;


import com.talenthire.assessment.dto.AssessmentRequest;
import com.talenthire.assessment.dto.AssessmentResponse;
import com.talenthire.assessment.dto.AssignAssessmentRequest;
import com.talenthire.assessment.dto.AssignAssessmentResponse;
import com.talenthire.assessment.dto.CandidateAssessmentResponse;
import com.talenthire.assessment.dto.CodeExecutionRequest;
import com.talenthire.assessment.dto.CodeExecutionResponse;
import com.talenthire.assessment.dto.CodingQuestionResponse;
import com.talenthire.assessment.dto.CreateAssessmentRequest;
import com.talenthire.assessment.dto.CreateAssessmentResponse;
import com.talenthire.assessment.dto.CreateCodingQuestionRequest;
import com.talenthire.assessment.dto.CreateCodingQuestionResponse;
import com.talenthire.assessment.dto.CreateTestCaseRequest;
import com.talenthire.assessment.dto.FinalAssessmentResultResponse;
import com.talenthire.assessment.dto.SubmitAssessmentRequest;
import com.talenthire.assessment.dto.SubmitAssessmentResponse;

public interface AssessmentService {

	public CodeExecutionResponse execute(CodeExecutionRequest request);

	public CreateAssessmentResponse createAssessment(CreateAssessmentRequest request, Integer recruiterId);

	public CreateCodingQuestionResponse addCodingQuestion(Integer assessmentId, Integer recruiterId, CreateCodingQuestionRequest request);

	public String addTestCase(Integer codingQuestionId, Integer recruiterId, CreateTestCaseRequest request);


	 AssessmentResponse createAssessment(AssessmentRequest request);

	AssessmentResponse getAssessmentById(Integer id);

	List<AssessmentResponse> getAllAssessments();

	AssessmentResponse updateAssessment(Integer id, AssessmentRequest request);

	void deleteAssessment(Integer id);

	public List<CodingQuestionResponse> getCodingQuestions(Integer assessmentId);
	
	AssessmentResponse getAssessmentByJobId(Integer jobId);

	public SubmitAssessmentResponse submitAssessment(SubmitAssessmentRequest request);
	
	AssignAssessmentResponse assignAssessment(AssignAssessmentRequest request);
	
	List<CandidateAssessmentResponse> getCandidateAssessments(Integer candidateId);
	
	List<FinalAssessmentResultResponse> getFinalAssessmentResults(Integer assessmentId);

	

}
