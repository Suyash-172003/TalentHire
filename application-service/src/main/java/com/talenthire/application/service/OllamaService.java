package com.talenthire.application.service;

import java.util.List;

import com.talenthire.application.dto.AtsScoreResponse;

public interface OllamaService {


	AtsScoreResponse extractSkills(String resumeText,List<String> jobSkills);

    String generateRemarks(
            List<String> jobSkills,
            List<String> resumeSkills);
}
