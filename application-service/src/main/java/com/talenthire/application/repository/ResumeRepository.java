package com.talenthire.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthire.application.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {

	Optional<Resume> findByCandidateId(Integer candidateId);

	List<Resume> findAllByCandidateId(Integer candidateId);
}
