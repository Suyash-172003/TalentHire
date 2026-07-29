package com.talenthire.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.talenthire.application.dto.ApplyJobResponse;
import com.talenthire.application.dto.CandidateDetailsRequest;
import com.talenthire.application.dto.CandidateDetailsResponse;
import com.talenthire.application.dto.JobApplicationResponse;
import com.talenthire.application.dto.JobResponse;
import com.talenthire.application.dto.JobSkillResponse;
import com.talenthire.application.dto.MyApplicationResponse;
import com.talenthire.application.dto.UploadResumeResponse;
import com.talenthire.application.dto.VerifyJobResponse;
import com.talenthire.application.entity.Application;
import com.talenthire.application.entity.ApplicationScreening;
import com.talenthire.application.entity.Resume;
import com.talenthire.application.entity.ResumeSkill;
import com.talenthire.application.entity.ScreeningStatus;
import com.talenthire.application.repository.ApplicationRepository;
import com.talenthire.application.repository.ApplicationScreeningRepository;
import com.talenthire.application.repository.ResumeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
	
	private final ResumeRepository resumeRepository;
	private final ApplicationScreeningRepository applicationScreeningRepository;
	private final ResumeService resumeService;
	private final JobClient jobClient;
	private final AuthClient authClient;
	private final ApplicationRepository applicationRepository;

	public ApplyJobResponse applyJob(MultipartFile resumeFile,
            Integer jobId,
            Integer candidateId) {


VerifyJobResponse job = jobClient.getJobById(jobId);

if (!job.getStatus().equals("OPEN")) {
throw new RuntimeException("Job is not accepting applications.");
}


if (applicationRepository.existsByJobIdAndCandidateId(jobId, candidateId)) {
throw new RuntimeException("You have already applied for this job.");
}


UploadResumeResponse uploadedResume =
resumeService.uploadResume(resumeFile, candidateId);


Resume resume = resumeRepository.findById(uploadedResume.getResumeId())
.orElseThrow(() -> new RuntimeException("Resume not found."));


Application application = new Application();
application.setCandidateId(candidateId);
application.setJobId(jobId);
application.setResume(resume);

Application saved = applicationRepository.save(application);

JobSkillResponse jobResponse=jobClient.getJobSkills(jobId);

List<String> jobSkills=jobResponse.getSkills();

List<ResumeSkill> skills=resume.getResumeSkills();

List<String> extractedSkills=new ArrayList<>();

for(ResumeSkill r: skills)
{
	extractedSkills.add(r.getSkillName());
}

ApplicationScreening applicationScreening=new ApplicationScreening();


Set<String> resumeSkillSet = extractedSkills.stream()
        .map(String::trim)
        .map(String::toLowerCase)
        .collect(Collectors.toSet());

Set<String> jobSkillSet = jobSkills.stream()
        .map(String::trim)
        .map(String::toLowerCase)
        .collect(Collectors.toSet());


Set<String> matchedSkills = new HashSet<>(resumeSkillSet);
matchedSkills.retainAll(jobSkillSet);


double matchPercentage = 0.0;

if (!jobSkillSet.isEmpty()) {
    matchPercentage =
            (matchedSkills.size() * 100.0) / jobSkillSet.size();
}

	     applicationScreening.setMatchPercentage(matchPercentage);

applicationScreening.setResumeScore(
        (int) Math.round(matchPercentage));

if (matchPercentage >= 80) {
    applicationScreening.setScreeningStatus(
            ScreeningStatus.SHORTLISTED);
}
else if (matchPercentage >= 60) {
    applicationScreening.setScreeningStatus(
            ScreeningStatus.UNDER_REVIEW);
}
else {
    applicationScreening.setScreeningStatus(
            ScreeningStatus.REJECTED);
}



applicationScreening.setApplication(application);

applicationScreeningRepository.save(applicationScreening);


ApplyJobResponse response = new ApplyJobResponse();
response.setApplicationId(saved.getApplicationId());
response.setStatus(saved.getStatus().name());
response.setMessage("Application submitted successfully.");

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

		        responses.add(response);
		    }

		    return responses;
	}

}
