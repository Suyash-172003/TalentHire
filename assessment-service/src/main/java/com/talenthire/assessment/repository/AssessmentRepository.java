package com.talenthire.assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthire.assessment.entity.Assessment;
import com.talenthire.assessment.entity.AssessmentStatus;
import com.talenthire.assessment.entity.AssessmentType;

public interface AssessmentRepository extends JpaRepository<Assessment, Integer> {


	   
	    List<Assessment> findByJobId(Integer jobId);

	    // Find by assessment type (MCQ/CODING)
	    List<Assessment> findByAssessmentType(AssessmentType assessmentType);

	   
	    List<Assessment> findByStatus(AssessmentStatus status);
}
