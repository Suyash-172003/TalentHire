package com.talenthire.assessmentservice.mcq.exam.service;

import java.util.List;

import com.talenthire.assessmentservice.mcq.exam.dto.AssessmentResultResponse;
import com.talenthire.assessmentservice.mcq.exam.dto.ExamResultResponse;
import com.talenthire.assessmentservice.mcq.exam.dto.StartExamRequest;
import com.talenthire.assessmentservice.mcq.exam.dto.StartExamResponse;
import com.talenthire.assessmentservice.mcq.exam.dto.SubmitExamRequest;

public interface MCQExamService {

    StartExamResponse startExam(StartExamRequest request);

    ExamResultResponse submitExam(Long attemptId,
                                  SubmitExamRequest request);

    ExamResultResponse getResult(Long attemptId);
    
    List<AssessmentResultResponse> getAssessmentResults(Long assessmentId);

}