package com.talenthire.assessment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talenthire.assessment.dto.MCQQuestionRequest;
import com.talenthire.assessment.dto.MCQQuestionResponse;
import com.talenthire.assessment.entity.Assessment;
import com.talenthire.assessment.entity.MCQQuestion;
import com.talenthire.assessment.exception.ResourceNotFoundException;
import com.talenthire.assessment.mapper.MCQMapper;
import com.talenthire.assessment.repository.AssessmentRepository;
import com.talenthire.assessment.repository.MCQQuestionRepository;

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
    public MCQQuestionResponse getQuestionById(Integer id) {

        MCQQuestion question = questionRepository.findById(id)
        		 .orElseThrow(() ->
                 new ResourceNotFoundException(
                         "Question not found with id: "
                         ));

        return mapper.toResponse(question);
    }

    @Override
    public List<MCQQuestionResponse> getQuestionsByAssessment(Integer assessmentId) {

        return questionRepository.findByAssessment_AssessmentId(assessmentId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MCQQuestionResponse updateQuestion(Integer id, MCQQuestionRequest request) {

    		//check
        throw new UnsupportedOperationException("Update API ");
    }

    @Override
    public void deleteQuestion(Integer id) {

        questionRepository.deleteById(id);
    }

}