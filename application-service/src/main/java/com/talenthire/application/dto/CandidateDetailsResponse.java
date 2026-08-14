package com.talenthire.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CandidateDetailsResponse {

    private Integer userId;

    private String name;

    private String email;

}