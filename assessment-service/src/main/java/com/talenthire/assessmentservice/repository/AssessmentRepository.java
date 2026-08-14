package com.talenthire.assessmentservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talenthire.assessmentservice.entity.Assessment;
import com.talenthire.assessmentservice.enums.AssessmentStatus;
import com.talenthire.assessmentservice.enums.AssessmentType;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

   
    List<Assessment> findByCompanyId(Long companyId);

   
    List<Assessment> findByJobId(Long jobId);

    // Find by assessment type (MCQ/CODING)
    List<Assessment> findByAssessmentType(AssessmentType assessmentType);

   
    List<Assessment> findByStatus(AssessmentStatus status);

}