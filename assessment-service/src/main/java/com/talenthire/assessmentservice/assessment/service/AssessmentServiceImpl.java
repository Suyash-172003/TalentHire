package com.talenthire.assessmentservice.assessment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talenthire.assessmentservice.assessment.dto.AssessmentRequest;
import com.talenthire.assessmentservice.assessment.dto.AssessmentResponse;
import com.talenthire.assessmentservice.assessment.entity.Assessment;
import com.talenthire.assessmentservice.assessment.mapper.AssessmentMapper;
import com.talenthire.assessmentservice.assessment.repository.AssessmentRepository;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private AssessmentMapper assessmentMapper;

    @Override
    public AssessmentResponse createAssessment(AssessmentRequest request) {

        Assessment assessment = assessmentMapper.toEntity(request);

        Assessment savedAssessment = assessmentRepository.save(assessment);

        return assessmentMapper.toResponse(savedAssessment);
    }

    @Override
    public AssessmentResponse getAssessmentById(Long id) {

        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        return assessmentMapper.toResponse(assessment);
    }

    @Override
    public List<AssessmentResponse> getAllAssessments() {

        List<Assessment> assessments = assessmentRepository.findAll();

        return assessments.stream()
                .map(assessmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AssessmentResponse updateAssessment(Long id, AssessmentRequest request) {

        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        assessment.setCompanyId(request.getCompanyId());
        assessment.setJobId(request.getJobId());
        assessment.setTitle(request.getTitle());
        assessment.setAssessmentType(request.getAssessmentType());
        assessment.setDuration(request.getDuration());
        assessment.setPassMarks(request.getPassMarks());
        assessment.setStartTime(request.getStartTime());
        assessment.setEndTime(request.getEndTime());
        assessment.setCreatedBy(request.getCreatedBy());
        assessment.setUpdatedAt(LocalDateTime.now());

        Assessment updatedAssessment = assessmentRepository.save(assessment);

        return assessmentMapper.toResponse(updatedAssessment);
    }

    @Override
    public void deleteAssessment(Long id) {

        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        assessmentRepository.delete(assessment);
    }
}