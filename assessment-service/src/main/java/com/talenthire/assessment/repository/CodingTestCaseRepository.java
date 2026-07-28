package com.talenthire.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthire.assessment.entity.CodingTestCase;

public interface CodingTestCaseRepository extends JpaRepository<CodingTestCase, Integer> {

}
