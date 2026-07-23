package com.talenthire.assessmentservice.mcq.exam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talenthire.assessmentservice.mcq.exam.entity.MCQExamAttempt;
import com.talenthire.assessmentservice.mcq.exam.enums.ExamStatus;

@Repository
public interface MCQExamAttemptRepository extends JpaRepository<MCQExamAttempt, Long> {

    // Check candidate attempt for an assessment
    Optional<MCQExamAttempt> findByAssessment_IdAndCandidateId(
            Long assessmentId,
            Long candidateId);

    // All attempts of a candidate
    List<MCQExamAttempt> findByCandidateId(Long candidateId);

    // All attempts of an assessment
    List<MCQExamAttempt> findByAssessment_Id(Long assessmentId);



    // Attempts by status
    List<MCQExamAttempt> findByStatus(ExamStatus status);

}