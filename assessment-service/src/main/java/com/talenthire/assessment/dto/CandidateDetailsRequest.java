package com.talenthire.assessment.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CandidateDetailsRequest {
	
	 private List<Integer> userIds;

}
