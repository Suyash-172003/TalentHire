package com.talenthire.application.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterviewScheduledEvent {

    private String candidateName;
    private String candidateEmail;
    private String jobTitle;
    private String interviewDate;
    private String meetingLink;
}
