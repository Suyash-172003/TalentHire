package com.talenthire.assessment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talenthire.assessment.entity.MCQExamAttempt;
import com.talenthire.assessment.entity.ExamStatus;

@Repository
public interface MCQExamAttemptRepository extends JpaRepository<MCQExamAttempt, Integer> {

    Optional<MCQExamAttempt> findByAssessment_AssessmentIdAndCandidateId(
            Integer assessmentId,
            Integer candidateId);

    List<MCQExamAttempt> findByCandidateId(Integer candidateId);

    List<MCQExamAttempt> findByAssessment_AssessmentId(Integer assessmentId);

    List<MCQExamAttempt> findByStatus(ExamStatus status);
    
    
}