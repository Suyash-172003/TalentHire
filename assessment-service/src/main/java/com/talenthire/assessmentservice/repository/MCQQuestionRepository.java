package com.talenthire.assessmentservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talenthire.assessmentservice.entity.MCQQuestion;

@Repository
public interface MCQQuestionRepository extends JpaRepository<MCQQuestion, Long> {

    // Get all questions of an assessment
    List<MCQQuestion> findByAssessment_Id(Long assessmentId);

}