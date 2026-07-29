package com.talenthire.application.service;

import java.util.List;

public interface OllamaService {


    List<String> extractSkills(String resumeText);

    String generateRemarks(
            List<String> jobSkills,
            List<String> resumeSkills);
}
