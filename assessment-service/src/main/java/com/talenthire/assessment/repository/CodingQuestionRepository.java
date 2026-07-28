package com.talenthire.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthire.assessment.entity.CodingQuestion;

public interface CodingQuestionRepository extends JpaRepository<CodingQuestion, Integer> {

}
