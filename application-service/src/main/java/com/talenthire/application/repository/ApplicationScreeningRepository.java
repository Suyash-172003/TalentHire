package com.talenthire.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthire.application.entity.ApplicationScreening;

public interface ApplicationScreeningRepository extends JpaRepository<ApplicationScreening, Integer> {

	Optional<ApplicationScreening> findByApplicationApplicationId(Integer applicationId);
	
}
