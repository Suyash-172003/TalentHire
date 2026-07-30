package com.talenthire.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talenthire.application.dto.AtsScoreResponse;
import com.talenthire.application.dto.OllamaRequest;
import com.talenthire.application.dto.OllamaResponse;

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
	public AtsScoreResponse extractSkills(String resumeText,List<String> jobSkills) {
		 try {
			 
			 System.out.println("Coming in the Extract Skills");

	            String prompt = buildSkillPrompt(resumeText,jobSkills);

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

	            AtsScoreResponse atsResponse =
	                    objectMapper.readValue(
	                            json,   
	                            AtsScoreResponse.class);


return atsResponse;

	        } catch (Exception e) {
	            throw new RuntimeException("Unable to extract skills", e);
	        }
	}

	private String buildSkillPrompt(String resumeText,List<String> jobSkills) {
		
		return """
				You are an ATS screening system.

				Compare the resume with the required job skills.

				Required Job Skills:
				%s

				Resume:
				%s

				Rules:
				- Compare skills semantically.
				- Spring Boot matches Spring.
				- Hibernate matches JPA.
				- REST API matches RESTful Services.
				- Apache Kafka matches Kafka.
				- JavaScript matches JS.
				- If no required skills are found, score must be 0.
				- If all required skills are found, score must be 100.
				- Return only valid JSON.
				- No markdown.
				- No explanation.

				Return exactly this JSON structure:

				{
				  "matchPercentage": <number>,
				  "resumeScore": <integer>
				}
				""".formatted(
				        String.join(", ", jobSkills),
				        resumeText
				);
	}

	@Override
	public String generateRemarks(List<String> jobSkills, List<String> resumeSkills) {
		// TODO Auto-generated method stub
		return null;
	}

}
