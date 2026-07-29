package com.talenthire.assessmentservice.service;

import java.util.List;

import com.talenthire.assessmentservice.dto.MCQQuestionRequest;
import com.talenthire.assessmentservice.dto.MCQQuestionResponse;

public interface MCQService {

    // Create Question
    MCQQuestionResponse createQuestion(MCQQuestionRequest request);

    // Get Question By Id
    MCQQuestionResponse getQuestionById(Long id);

    // Get All Questions of an Assessment
    List<MCQQuestionResponse> getQuestionsByAssessment(Long assessmentId);

    // Update Question
    MCQQuestionResponse updateQuestion(Long id, MCQQuestionRequest request);

    // Delete Question
    void deleteQuestion(Long id);

}