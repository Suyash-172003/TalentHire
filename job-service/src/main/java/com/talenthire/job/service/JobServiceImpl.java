package com.talenthire.job.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.talenthire.job.dto.CreateJobRequest;
import com.talenthire.job.dto.CreateJobResponse;
import com.talenthire.job.entity.EmploymentType;
import com.talenthire.job.entity.Job;
import com.talenthire.job.entity.JobSkill;
import com.talenthire.job.entity.WorkMode;
import com.talenthire.job.repository.JobRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
	
	private final JobRepository jobRepository;
	
	
	public CreateJobResponse createJob(CreateJobRequest request) {
		Job job=new Job();
		job.setDescription(request.getDescription());
		job.setEmploymentType(EmploymentType.valueOf(request.getEmploymentType()));
		job.setExperienceRequired(request.getExperienceRequired());
		job.setLocation(request.getLocation());
		job.setSalary(request.getSalary());
		job.setTitle(request.getTitle());
		job.setWorkMode(WorkMode.valueOf(request.getWorkMode()));
		
		List<JobSkill> skills=new ArrayList<>();
		
		for(String skill: request.getSkills())
		{
			JobSkill jobSkill=new JobSkill();
			jobSkill.setSkillName(skill);
			jobSkill.setJob(job);
			
			skills.add(jobSkill);
		}
		
		job.setJobSkills(skills);
		
		Job saved=jobRepository.save(job);
		
		CreateJobResponse response=new CreateJobResponse();
		
		response.setJobId(saved.getJobId());
		response.setStatus(saved.getStatus().name());
		response.setMessage("Job Created Successfully");
		response.setCreatedAt(saved.getCreatedAt());
		
		return response;
		
		
	}

}
