package com.talenthire.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talenthire.application.dto.OllamaRequest;
import com.talenthire.application.dto.OllamaResponse;
import com.talenthire.application.dto.SkillsExtractionResponse;

@Service
public class OllamaServiceImpl implements OllamaService {
	
	 @Value("${ollama.url}")
	    private String ollamaUrl;

	    @Value("${ollama.model}")
	    private String model;
	    
	    @Autowired
	    private RestTemplate restTemplate;

	    @Autowired
	    private ObjectMapper objectMapper;

	@Override
	public List<String> extractSkills(String resumeText) {
		 try {
			 
			 System.out.println("Coming in the Extract Skills");

	            String prompt = buildSkillPrompt(resumeText);

	            OllamaRequest request =
	                    new OllamaRequest(model, prompt, false);

	            OllamaResponse response =
	                    restTemplate.postForObject(
	                            ollamaUrl,
	                            request,
	                            OllamaResponse.class);
	            
	            String json = response.getResponse()
	                    .replaceFirst("^```json\\s*", "")
	                    .replaceFirst("^```\\s*", "")
	                    .replaceFirst("\\s*```$", "")
	                    .trim();

	            System.out.println("========== RAW RESPONSE ==========");
	            System.out.println(response.getResponse());

	            System.out.println("========== CLEAN JSON ==========");
	            System.out.println(json);

	            SkillsExtractionResponse skillResponse =
	                    objectMapper.readValue(
	                            json,   
	                            SkillsExtractionResponse.class);

	            return skillResponse.getSkills();

	        } catch (Exception e) {
	            throw new RuntimeException("Unable to extract skills", e);
	        }
	}

	private String buildSkillPrompt(String resumeText) {
		
		System.out.println("Resume length: " + resumeText.length());
		
		return """ 
				You are an ATS resume parser.

		Extract ALL technical skills explicitly mentioned in the resume.

		Include programming languages, frameworks, libraries, databases, APIs, cloud platforms, DevOps tools, build tools, testing tools, version control systems, operating systems, message brokers and other technologies.

		Rules:
		- Return ONLY valid JSON.
		- The response MUST begin with '{' and end with '}'.
		- Do NOT use markdown.
		- Do NOT use ```json.
		- Do NOT write any explanation.
		- Do NOT return objects.
		- Do NOT return descriptions.
		- Remove duplicate skills.
		- Do NOT invent skills.

		Output:

		{
		  "skills": [
		    "Java",
		    "Spring Boot",
		    "Hibernate",
		    "MySQL",
		    "Git"
		  ]
		}

		Resume:

		""" + resumeText;
	}

	@Override
	public String generateRemarks(List<String> jobSkills, List<String> resumeSkills) {
		// TODO Auto-generated method stub
		return null;
	}

}
