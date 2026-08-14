package com.talenthire.application.service;

import java.util.List;

import com.talenthire.application.dto.InterviewResponse;
import com.talenthire.application.dto.ScheduleInterviewRequest;

public interface InterviewService {

    InterviewResponse scheduleInterview(
            ScheduleInterviewRequest request
    );

    InterviewResponse getInterviewByApplication(
            Integer applicationId
    );

    List<InterviewResponse> getInterviewsByCandidate(
            Integer candidateId
    );
}