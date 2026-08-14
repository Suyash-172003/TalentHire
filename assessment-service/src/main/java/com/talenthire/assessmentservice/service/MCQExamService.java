package com.talenthire.assessmentservice.service;

import java.util.List;

import com.talenthire.assessmentservice.dto.AssessmentResultResponse;
import com.talenthire.assessmentservice.dto.ExamResultResponse;
import com.talenthire.assessmentservice.dto.StartExamRequest;
import com.talenthire.assessmentservice.dto.StartExamResponse;
import com.talenthire.assessmentservice.dto.SubmitExamRequest;

public interface MCQExamService {

    StartExamResponse startExam(StartExamRequest request);

    ExamResultResponse submitExam(Long attemptId,
                                  SubmitExamRequest request);

    ExamResultResponse getResult(Long attemptId);
    
    List<AssessmentResultResponse> getAssessmentResults(Long assessmentId);

}