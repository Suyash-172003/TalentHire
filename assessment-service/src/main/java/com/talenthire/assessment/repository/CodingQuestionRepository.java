package com.talenthire.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthire.assessment.entity.CodingQuestion;
import java.util.List;


public interface CodingQuestionRepository extends JpaRepository<CodingQuestion, Integer> {

	List<CodingQuestion> findByAssessmentAssessmentIdOrderByQuestionOrder(Integer assessmentId);

	
}
