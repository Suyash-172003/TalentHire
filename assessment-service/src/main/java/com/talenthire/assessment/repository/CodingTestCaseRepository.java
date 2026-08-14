package com.talenthire.assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthire.assessment.entity.CodingTestCase;

public interface CodingTestCaseRepository extends JpaRepository<CodingTestCase, Integer> {
	 List<CodingTestCase> findByCodingQuestionCodingQuestionId(Integer id);
}
