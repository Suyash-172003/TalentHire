package com.talenthire.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthire.application.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	boolean existsByJobIdAndCandidateId(Integer jobId, Integer candidateId);

	List<Application> findByCandidateId(Integer candidateId);

	List<Application> findByJobId(Integer jobId);

}
