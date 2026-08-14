package com.talenthire.application.repository;

import com.talenthire.application.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Integer> {

    Optional<Interview> findByApplication_ApplicationId(Integer applicationId);

    List<Interview> findByApplication_CandidateId(Integer candidateId);
}