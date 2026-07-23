package com.talenthire.assessmentservice.mcq.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talenthire.assessmentservice.assessment.entity.Assessment;
import com.talenthire.assessmentservice.assessment.repository.AssessmentRepository;
import com.talenthire.assessmentservice.mcq.dto.MCQQuestionRequest;
import com.talenthire.assessmentservice.mcq.dto.MCQQuestionResponse;
import com.talenthire.assessmentservice.mcq.entity.MCQQuestion;
import com.talenthire.assessmentservice.mcq.mapper.MCQMapper;
import com.talenthire.assessmentservice.mcq.repository.MCQQuestionRepository;
import com.talenthire.assessmentservice.mcq.service.MCQService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MCQServiceImpl implements MCQService {

    private final MCQQuestionRepository questionRepository;
    private final AssessmentRepository assessmentRepository;
    private final MCQMapper mapper;

    @Override
    public MCQQuestionResponse createQuestion(MCQQuestionRequest request) {

        Assessment assessment = assessmentRepository.findById(request.getAssessmentId())
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        MCQQuestion question = mapper.toEntity(request, assessment);

        MCQQuestion savedQuestion = questionRepository.save(question);

        return mapper.toResponse(savedQuestion);
    }

    @Override
    public MCQQuestionResponse getQuestionById(Long id) {

        MCQQuestion question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        return mapper.toResponse(question);
    }

    @Override
    public List<MCQQuestionResponse> getQuestionsByAssessment(Long assessmentId) {

        return questionRepository.findByAssessment_Id(assessmentId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MCQQuestionResponse updateQuestion(Long id, MCQQuestionRequest request) {

    		//check
        throw new UnsupportedOperationException("Update API ");
    }

    @Override
    public void deleteQuestion(Long id) {

        questionRepository.deleteById(id);
    }

}