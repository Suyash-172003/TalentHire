package com.talenthire.assessment.service;

import java.util.List;

import com.talenthire.assessment.dto.AssessmentResultResponse;
import com.talenthire.assessment.dto.ExamResultResponse;
import com.talenthire.assessment.dto.StartExamRequest;
import com.talenthire.assessment.dto.StartExamResponse;
import com.talenthire.assessment.dto.SubmitExamRequest;

public interface MCQExamService {

    StartExamResponse startExam(StartExamRequest request);

    ExamResultResponse submitExam(Integer attemptId,
                                  SubmitExamRequest request);

    ExamResultResponse getResult(Integer attemptId);
    
    List<AssessmentResultResponse> getAssessmentResults(Integer assessmentId);

}