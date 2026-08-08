package com.talenthire.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentAssignedEvent {

    private String candidateName;
    private String candidateEmail;
    private String assessmentTitle;
}