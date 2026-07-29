package com.talenthire.assessment.service;

import java.util.List;

import com.talenthire.assessment.dto.MCQQuestionRequest;
import com.talenthire.assessment.dto.MCQQuestionResponse;

public interface MCQService {

    // Create Question
    MCQQuestionResponse createQuestion(MCQQuestionRequest request);

    // Get Question By Id
    MCQQuestionResponse getQuestionById(Integer id);

    // Get All Questions of an Assessment
    List<MCQQuestionResponse> getQuestionsByAssessment(Integer assessmentId);

    // Update Question
    MCQQuestionResponse updateQuestion(Integer id, MCQQuestionRequest request);

    // Delete Question
    void deleteQuestion(Integer id);

}