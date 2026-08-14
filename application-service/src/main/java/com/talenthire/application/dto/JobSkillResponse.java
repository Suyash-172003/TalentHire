package com.talenthire.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobSkillResponse {

    private Integer jobId;
    private String title;
    private Integer shortlistScore;

    private List<String> skills;

}