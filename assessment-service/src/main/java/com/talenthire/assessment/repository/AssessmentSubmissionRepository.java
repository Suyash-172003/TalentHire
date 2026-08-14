package com.talenthire.assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthire.assessment.entity.AssessmentSubmission;

public interface AssessmentSubmissionRepository extends JpaRepository<AssessmentSubmission, Integer>  {

	List<AssessmentSubmission> findByAssessment_AssessmentId(Integer assessmentId);
}
