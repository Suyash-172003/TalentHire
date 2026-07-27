package com.talenthire.job.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.talenthire.job.dto.CreateJobRequest;
import com.talenthire.job.dto.CreateJobResponse;
import com.talenthire.job.dto.JobResponse;
import com.talenthire.job.dto.VerifyJobResponse;
import com.talenthire.job.entity.EmploymentType;
import com.talenthire.job.entity.Job;
import com.talenthire.job.entity.JobSkill;
import com.talenthire.job.entity.JobStatus;
import com.talenthire.job.entity.WorkMode;
import com.talenthire.job.repository.JobRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
	
	private final JobRepository jobRepository;
	
	
	public CreateJobResponse createJob(CreateJobRequest request,Integer recruiterId) {
		Job job=new Job();
		job.setCompanyName(request.getCompanyName());
		job.setRecruiterID(recruiterId);
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


	@Override
	public List<JobResponse> getAllJobs() {
		List<Job> jobs= jobRepository.findByStatus(JobStatus.OPEN);
		
		List<JobResponse> jobResponses=jobs.stream().map(j->convertToResponse(j)).toList();
		
		return jobResponses;
	}
	
	private JobResponse convertToResponse(Job job) {

	    JobResponse response = new JobResponse();

	    response.setJobId(job.getJobId());
	    response.setTitle(job.getTitle());
	    response.setDescription(job.getDescription());
	    response.setCompanyName(job.getCompanyName());

	    response.setLocation(job.getLocation());
	    response.setSalary(job.getSalary());
	    response.setExperience(job.getExperienceRequired());
	    response.setEmploymentType(job.getEmploymentType());
	    response.setWorkMode(job.getWorkMode());
	    response.setStatus(job.getStatus());

	    response.setSkills(
	    	    job.getJobSkills()
	    	        .stream()
	    	        .map(jobSkill -> jobSkill.getSkillName())
	    	        .toList()
	    	);
	    

	    return response;
	}


	@Override
	public JobResponse getJobById(Integer jobId) {
		Job job=jobRepository.findById(jobId).orElseThrow(()-> new RuntimeException("Job not found"));
		
		
		return convertToResponse(job);
	}


	@Override
	public List<JobResponse> getMyJobs(Integer recruiterId) {
List<Job> jobs= jobRepository.findByRecruiterID(recruiterId);
		
		List<JobResponse> jobResponse=jobs.stream().map(j->convertToResponse(j)).toList();
		
		return jobResponse;
	}


	@Override
	public CreateJobResponse updateJob(Integer jobId, Integer recruiterId, CreateJobRequest request) {
		Job existingJob=jobRepository.findById(jobId).orElseThrow(()-> new RuntimeException("Job not found"));	
		

if (!existingJob.getRecruiterID().equals(recruiterId)) {
    throw new RuntimeException("You cannot update this job");
}

existingJob.setTitle(request.getTitle());
existingJob.setDescription(request.getDescription());
existingJob.setLocation(request.getLocation());
existingJob.setSalary(request.getSalary());
existingJob.setExperienceRequired(request.getExperienceRequired());
existingJob.setEmploymentType(
        EmploymentType.valueOf(request.getEmploymentType()));
existingJob.setWorkMode(
        WorkMode.valueOf(request.getWorkMode()));
existingJob.setRecruiterID(recruiterId);

existingJob.getJobSkills().clear();



for(String skill: request.getSkills())
{
	JobSkill jobSkill=new JobSkill();
	jobSkill.setSkillName(skill);
	jobSkill.setJob(existingJob);
	
	 existingJob.getJobSkills().add(jobSkill);
	
}



Job saved=jobRepository.save(existingJob);


CreateJobResponse response=new CreateJobResponse();

response.setJobId(saved.getJobId());
response.setStatus(saved.getStatus().name());
response.setMessage("Job updated Successfully");
response.setCreatedAt(saved.getCreatedAt());

return response;
		
	}


	@Override
	public CreateJobResponse closeJob(Integer jobId, Integer recruiterId) {
		Job job = jobRepository.findById(jobId)
	            .orElseThrow(() -> new RuntimeException("Job not found"));

	    if (!job.getRecruiterID().equals(recruiterId)) {
	        throw new RuntimeException("You cannot close this job");
	    }

	    job.setStatus(JobStatus.CLOSED);

	    Job saved = jobRepository.save(job);

	    CreateJobResponse response = new CreateJobResponse();

	    response.setJobId(saved.getJobId());
	    response.setStatus(saved.getStatus().name());
	    response.setMessage("Job updated Successfully");
	    response.setCreatedAt(saved.getCreatedAt());

	    return response;
	}


	
	public VerifyJobResponse verifyJob(Integer jobId) {
		Job job = jobRepository.findById(jobId)
	            .orElseThrow(() -> new RuntimeException("Job not found"));

	    VerifyJobResponse response = new VerifyJobResponse();

	    response.setJobId(job.getJobId());
	    response.setStatus(job.getStatus().name());
	    response.setRecruiterId(job.getRecruiterID());

	    return response;
		
	}
	
	

	
	
}
