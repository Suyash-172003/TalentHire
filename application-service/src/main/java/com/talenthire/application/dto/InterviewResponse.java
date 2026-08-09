package com.talenthire.application.dto;

import java.time.LocalDateTime;

import com.talenthire.application.entity.InterviewStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewResponse {

    private Integer interviewId;

    private Integer applicationId;

    private Integer candidateId;

    private LocalDateTime interviewDate;

    private String meetingLink;

    private InterviewStatus status;

    private String feedback;

    private String jobTitle;

    private String companyName;

    public InterviewResponse(
            Integer interviewId,
            Integer applicationId,
            Integer candidateId,
            LocalDateTime interviewDate,
            String meetingLink,
            InterviewStatus status,
            String feedback,
            String jobTitle,
            String companyName
    ) {

        this.interviewId = interviewId;
        this.applicationId = applicationId;
        this.candidateId = candidateId;
        this.interviewDate = interviewDate;
        this.meetingLink = meetingLink;
        this.status = status;
        this.feedback = feedback;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
    }
}