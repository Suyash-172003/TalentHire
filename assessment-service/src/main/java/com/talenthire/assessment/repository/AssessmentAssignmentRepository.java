package com.talenthire.assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthire.assessment.entity.AssessmentAssignment;

public interface AssessmentAssignmentRepository
        extends JpaRepository<AssessmentAssignment, Integer> {

    List<AssessmentAssignment> findByCandidateId(Integer candidateId);

    boolean existsByApplicationId(Integer applicationId);

}