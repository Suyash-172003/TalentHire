package com.talenthire.job.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthire.job.entity.Job;
import com.talenthire.job.entity.JobStatus;


public interface JobRepository extends JpaRepository<Job, Integer> {

	List<Job> findByStatus(JobStatus status);
	
	List<Job> findByRecruiterID(Integer recruiterID);
}
