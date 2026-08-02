package com.talenthire.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talenthire.application.dto.AtsScoreResponse;
import com.talenthire.application.dto.JobSkillResponse;
import com.talenthire.application.dto.ScreenApplicationResponse;
import com.talenthire.application.entity.Application;
import com.talenthire.application.entity.ApplicationScreening;
import com.talenthire.application.entity.Resume;
import com.talenthire.application.entity.ScreeningStatus;
import com.talenthire.application.repository.ApplicationRepository;
import com.talenthire.application.repository.ApplicationScreeningRepository;
import com.talenthire.application.util.ResumeTextExtractor;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService{

	private final ApplicationScreeningRepository applicationScreeningRepository;
	private final JobClient jobClient;
	private final GroqService groqService;
	private final ApplicationRepository applicationRepository;
	private final ResumeService resumeService;
	private final ResumeTextExtractor resumeTextExtractor;
	
	@Override
	public ScreenApplicationResponse screenApplication(Integer applicationId,String resumeText) {
		 Application application =
	                applicationRepository.findById(applicationId)
	                .orElseThrow(() -> new RuntimeException("Application not found"));

	        Resume resume = application.getResume();


	        JobSkillResponse jobResponse =
	                jobClient.getJobSkills(application.getJobId());

	        List<String> jobSkills = jobResponse.getSkills();

	        AtsScoreResponse score =
	        		groqService.extractSkills(resumeText, jobSkills);

	        ApplicationScreening screening =
	                new ApplicationScreening();

	        screening.setApplication(application);

	        screening.setMatchPercentage(score.getMatchPercentage());

	        screening.setResumeScore(score.getResumeScore());

	        Integer cutoff = jobResponse.getShortlistScore();

	        if (score.getResumeScore() >= cutoff) {
	            screening.setScreeningStatus(ScreeningStatus.SHORTLISTED);
	        } else {
	            screening.setScreeningStatus(ScreeningStatus.REJECTED);
	        }
	       

	        ApplicationScreening saved=  applicationScreeningRepository.save(screening);
	        
	        ScreenApplicationResponse response =new ScreenApplicationResponse();
	        response.setScreeningStatus(saved.getScreeningStatus());
	        response.setMatchPercentage(saved.getMatchPercentage());
	        response.setResumeScore(saved.getResumeScore());
	        return response;
	    }
		
	}


