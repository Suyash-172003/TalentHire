package com.talenthire.application.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.talenthire.application.dto.ApplicationAppliedEvent;
import com.talenthire.application.dto.ApplyJobResponse;
import com.talenthire.application.dto.CandidateDetailsRequest;
import com.talenthire.application.dto.CandidateDetailsResponse;
import com.talenthire.application.dto.JobApplicationResponse;
import com.talenthire.application.dto.JobResponse;
import com.talenthire.application.dto.MyApplicationResponse;
import com.talenthire.application.dto.ScreenApplicationResponse;
import com.talenthire.application.dto.UploadResumeResponse;
import com.talenthire.application.dto.VerifyJobResponse;
import com.talenthire.application.entity.Application;
import com.talenthire.application.entity.ApplicationScreening;
import com.talenthire.application.entity.Resume;
import com.talenthire.application.repository.ApplicationRepository;
import com.talenthire.application.repository.ApplicationScreeningRepository;
import com.talenthire.application.repository.ResumeRepository;
import com.talenthire.application.util.ResumeTextExtractor;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
	
	private final ScreeningService screeningService;
	private final ResumeRepository resumeRepository;
	private final ResumeService resumeService;
	private final JobClient jobClient;
	private final AuthClient authClient;
	private final KafkaProducerService kafkaProducerService;
	private final ApplicationRepository applicationRepository;
	private final ResumeTextExtractor resumeTextExtractor;


	private final ApplicationScreeningRepository applicationScreeningRepository;


	@Override
	public ApplyJobResponse applyJob(
	        MultipartFile resumeFile,
	        Integer jobId,
	        Integer candidateId) {

	    VerifyJobResponse job = jobClient.getJobById(jobId);

	    if (!job.getStatus().equals("OPEN")) {
	        throw new RuntimeException("Job is not accepting applications.");
	    }

	    if (applicationRepository.existsByJobIdAndCandidateId(jobId, candidateId)) {
	        throw new RuntimeException("You have already applied.");
	    }
	    
	    String resumeText="";
	    try {
		 resumeText = resumeTextExtractor.extractText(resumeFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    UploadResumeResponse uploadedResume =
	            resumeService.uploadResume(resumeFile, candidateId);

	    Resume resume = resumeRepository.findById(uploadedResume.getResumeId())
	            .orElseThrow(() -> new RuntimeException("Resume not found"));

	    Application application = new Application();

	    application.setCandidateId(candidateId);
	    application.setJobId(jobId);
	    application.setResume(resume);

	    Application saved = applicationRepository.save(application);
	    
	    List<Integer> candidateIds = new ArrayList<>();
	    candidateIds.add(candidateId);
	    
	    CandidateDetailsRequest request =
	            new CandidateDetailsRequest();

	    request.setUserIds(candidateIds);
	    
	    List<CandidateDetailsResponse> candidates =
	            authClient.getCandidateDetails(request);
	    
	    CandidateDetailsResponse candidate =
                candidates.get(0);
	    
	    
	    
	    ApplicationAppliedEvent event = new ApplicationAppliedEvent();

	    event.setCandidateName(candidate.getName());
	    event.setCandidateEmail(candidate.getEmail());
	    event.setJobTitle(job.getDescription());
	    
	    kafkaProducerService.send(event);

	    ScreenApplicationResponse screenResponse=screeningService.screenApplication(saved.getApplicationId(),resumeText);

	    ApplyJobResponse response = new ApplyJobResponse();

	    response.setApplicationId(saved.getApplicationId());
	    response.setStatus(saved.getStatus().name());
	    response.setMessage("Application submitted successfully.");
	    response.setScreeningStatus(screenResponse.getScreeningStatus());
	    

	    return response;
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
		    
		    List<Integer> candidateIds = new ArrayList<>();

		    for (Application application : applications) {
		        candidateIds.add(application.getCandidateId());
		    }
		    
		    CandidateDetailsRequest request =
		            new CandidateDetailsRequest();

		    request.setUserIds(candidateIds);
		    
		    List<CandidateDetailsResponse> candidates =
		            authClient.getCandidateDetails(request);

		    List<JobApplicationResponse> responses =
		            new ArrayList<>();
		    
		    Map<Integer, CandidateDetailsResponse> candidateMap =
		            new HashMap<>();

		    for (CandidateDetailsResponse candidate : candidates) {
		        candidateMap.put(candidate.getUserId(), candidate);
		    }


		    for (Application application : applications) {
		    	
		    	CandidateDetailsResponse candidate =
		                candidateMap.get(application.getCandidateId());

		        JobApplicationResponse response =
		                new JobApplicationResponse();

		        response.setApplicationId(
		                application.getApplicationId());

		        response.setCandidateId(
		                application.getCandidateId());

		        response.setApplicationStatus(
		                application.getStatus().name());
		        
		        response.setCandidateName(
		                candidate.getName());

		        response.setCandidateEmail(
		                candidate.getEmail());

		        response.setResumeId(
		                application.getResume().getResumeId());
		        
		        ApplicationScreening screening =
		                applicationScreeningRepository
		                        .findByApplicationApplicationId(application.getApplicationId())
		                        .orElse(null);

		        if (screening != null) {
		            response.setScreeningStatus(
		                    screening.getScreeningStatus().name());
		        }

		        responses.add(response);
		    }

		    return responses;
	}

}
