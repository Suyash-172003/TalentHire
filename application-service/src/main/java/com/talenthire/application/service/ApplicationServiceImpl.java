package com.talenthire.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.talenthire.application.dto.ApplyJobResponse;
import com.talenthire.application.dto.JobApplicationResponse;
import com.talenthire.application.dto.JobResponse;
import com.talenthire.application.dto.MyApplicationResponse;
import com.talenthire.application.dto.VerifyJobResponse;
import com.talenthire.application.entity.Application;
import com.talenthire.application.entity.Resume;
import com.talenthire.application.repository.ApplicationRepository;
import com.talenthire.application.repository.ResumeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
	
	private final ResumeRepository resumeRepository;
	private final JobClient jobClient;
	private final ApplicationRepository applicationRepository;

	@Override
	public ApplyJobResponse applyJob(Integer jobId, Integer candidateId) {
		Resume resume= resumeRepository.findByCandidateId(candidateId).orElseThrow(()-> new RuntimeException("First Upload Resume"));
		
		VerifyJobResponse response=jobClient.getJobById(jobId);
		
		if(!response.getStatus().equals("OPEN"))
		{
			 throw new RuntimeException("Job is not accepting applications.");
		}
		
		if (applicationRepository.existsByJobIdAndCandidateId(
	            jobId,
	            candidateId)) {

	        throw new RuntimeException(
	                "You have already applied for this job.");
	    }
		
		Application application=new Application();
		application.setResume(resume);
		application.setCandidateId(candidateId);
		application.setJobId(jobId);

		
		Application saved = applicationRepository.save(application);

	    ApplyJobResponse applyJobResponse = new ApplyJobResponse();
	    applyJobResponse.setApplicationId(saved.getApplicationId());
	    applyJobResponse.setStatus(saved.getStatus().name());
	    applyJobResponse.setMessage("Application submitted successfully.");
	  

	    return applyJobResponse;
		

	}

	@Override
	public List<MyApplicationResponse>  getMyApplications(Integer candidateId) {
		List<Application> applications= applicationRepository.findByCandidateId(candidateId);
		
		List<MyApplicationResponse> responses = new ArrayList<>();
		
		for (Application application : applications) {

		    JobResponse job = jobClient.getJob(application.getJobId());

		    MyApplicationResponse response = new MyApplicationResponse();

		    response.setApplicationId(application.getApplicationId());
		    response.setJobId(application.getJobId());
		    response.setJobTitle(job.getTitle());
		    response.setDescription(job.getDescription());
		    response.setLocation(job.getLocation());
		    response.setApplicationStatus(application.getStatus().name());
		    

		    responses.add(response);
		}

		return responses;
		
	}

	
	public List<JobApplicationResponse> getApplicationsByJob(Integer jobId, Integer recruiterId) {
		VerifyJobResponse job=jobClient.getJobById(jobId);

		    if (!job.getRecruiterId().equals(recruiterId)) {
		        throw new RuntimeException("You are not authorized to view these applications.");
		    }

		    List<Application> applications =
		            applicationRepository.findByJobId(jobId);

		    List<JobApplicationResponse> responses =
		            new ArrayList<>();

		    for (Application application : applications) {

		        JobApplicationResponse response =
		                new JobApplicationResponse();

		        response.setApplicationId(
		                application.getApplicationId());

		        response.setCandidateId(
		                application.getCandidateId());

		        response.setApplicationStatus(
		                application.getStatus().name());

		        responses.add(response);
		    }

		    return responses;
	}

}
