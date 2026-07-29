package com.talenthire.assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talenthire.assessment.entity.MCQQuestion;

@Repository
public interface MCQQuestionRepository extends JpaRepository<MCQQuestion, Integer> {

    List<MCQQuestion> findByAssessment_AssessmentId(Integer assessmentId);

}