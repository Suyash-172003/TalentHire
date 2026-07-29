package com.talenthire.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthire.assessment.entity.Assessment;

public interface AssessmentRepository extends JpaRepository<Assessment, Integer> {
	

}
