package com.talenthire.notification.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewScheduledEvent {

    private String candidateName;
    private String candidateEmail;
    private String jobTitle;
    private String interviewDate;
    private String meetingLink;
}