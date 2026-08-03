package com.talenthire.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthire.assessment.entity.Assessment;
import com.talenthire.assessment.entity.CodingQuestion;
import java.util.List;
import java.util.Optional;


public interface CodingQuestionRepository extends JpaRepository<CodingQuestion, Integer> {

	List<CodingQuestion> findByAssessmentAssessmentIdOrderByQuestionOrder(Integer assessmentId);

	List<CodingQuestion> findByAssessmentAssessmentId(Integer assessmentId);

	
}
