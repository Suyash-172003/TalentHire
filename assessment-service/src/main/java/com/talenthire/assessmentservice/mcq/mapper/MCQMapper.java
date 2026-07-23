package com.talenthire.assessmentservice.mcq.mapper;

import org.springframework.stereotype.Component;

import com.talenthire.assessmentservice.assessment.entity.Assessment;
import com.talenthire.assessmentservice.mcq.dto.MCQQuestionRequest;
import com.talenthire.assessmentservice.mcq.dto.MCQQuestionResponse;
import com.talenthire.assessmentservice.mcq.entity.MCQQuestion;

@Component
public class MCQMapper {

    // Request DTO -> Entity
    public MCQQuestion toEntity(MCQQuestionRequest request, Assessment assessment) {

        return MCQQuestion.builder()
                .assessment(assessment)
                .questionText(request.getQuestionText())
                .optionA(request.getOptionA())
                .optionB(request.getOptionB())
                .optionC(request.getOptionC())
                .optionD(request.getOptionD())
                .correctAnswer(request.getCorrectAnswer())
                .difficulty(request.getDifficulty())
                .marks(request.getMarks())
                .questionOrder(request.getQuestionOrder())
                .build();
    }

    // Entity -> Response DTO
    public MCQQuestionResponse toResponse(MCQQuestion question) {

        return MCQQuestionResponse.builder()
                .id(question.getId())
                .assessmentId(question.getAssessment().getId())
                .questionText(question.getQuestionText())
                .optionA(question.getOptionA())
                .optionB(question.getOptionB())
                .optionC(question.getOptionC())
                .optionD(question.getOptionD())
                .correctAnswer(question.getCorrectAnswer())
                .difficulty(question.getDifficulty())
                .marks(question.getMarks())
                .questionOrder(question.getQuestionOrder())
                .build();
    }
}