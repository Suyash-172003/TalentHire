package com.talenthire.application.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleInterviewRequest {

    private Integer applicationId;

    private LocalDateTime interviewDate;

    private String meetingLink;
}