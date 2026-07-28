package com.talenthire.assessment.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAssessmentResponse {

	private Integer assessmentId;

    private String status;

    private String message;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
