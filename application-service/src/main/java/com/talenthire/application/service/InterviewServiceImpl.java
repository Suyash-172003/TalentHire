package com.talenthire.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.talenthire.application.dto.InterviewResponse;
import com.talenthire.application.dto.JobResponse;
import com.talenthire.application.dto.ScheduleInterviewRequest;
import com.talenthire.application.entity.Application;
import com.talenthire.application.entity.Interview;
import com.talenthire.application.entity.InterviewStatus;
import com.talenthire.application.repository.ApplicationRepository;
import com.talenthire.application.repository.InterviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;

    private final ApplicationRepository applicationRepository;

    private final JobClient jobClient;

    @Override
    public InterviewResponse scheduleInterview(
            ScheduleInterviewRequest request) {

        Application application =
                applicationRepository
                        .findById(request.getApplicationId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"
                                )
                        );

        if (interviewRepository
                .findByApplication_ApplicationId(
                        request.getApplicationId()
                )
                .isPresent()) {

            throw new RuntimeException(
                    "Interview already scheduled for this application"
            );
        }

        Interview interview = new Interview();

        interview.setApplication(application);

        interview.setInterviewDate(
                request.getInterviewDate()
        );

        interview.setMeetingLink(
                request.getMeetingLink()
        );

        interview.setStatus(
                InterviewStatus.SCHEDULED
        );

        Interview savedInterview =
                interviewRepository.save(interview);

        return convertToResponse(savedInterview);
    }

    @Override
    public InterviewResponse getInterviewByApplication(
            Integer applicationId) {

        return interviewRepository
                .findByApplication_ApplicationId(applicationId)
                .map(this::convertToResponse)
                .orElse(null);
    }

    @Override
    public List<InterviewResponse> getInterviewsByCandidate(
            Integer candidateId) {

        return interviewRepository
                .findByApplication_CandidateId(candidateId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private InterviewResponse convertToResponse(
            Interview interview) {

        Application application =
                interview.getApplication();

        Integer jobId =
                application.getJobId();

        JobResponse job =
                jobClient.getJob(jobId);

        return new InterviewResponse(

                interview.getInterviewId(),

                application.getApplicationId(),

                application.getCandidateId(),

                interview.getInterviewDate(),

                interview.getMeetingLink(),

                interview.getStatus(),

                interview.getFeedback(),

                job.getTitle(),

                job.getCompanyName()
        );
    }
}