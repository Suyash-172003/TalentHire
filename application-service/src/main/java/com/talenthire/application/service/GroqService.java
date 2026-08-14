package com.talenthire.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.talenthire.application.dto.AtsScoreResponse;
import com.talenthire.application.exception.ExternalServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroqService {

    private final OpenAIClient client;
    private final ObjectMapper objectMapper;

    public AtsScoreResponse extractSkills(String resumeText, List<String> jobSkills) {

    	String prompt = """
    			You are an expert ATS (Applicant Tracking System).

    			Your task is to evaluate the candidate's resume against the required job skills.

    			Required Skills:
    			%s

    			Resume:
    			%s

    			Instructions:
    			1. Compare the resume with the required skills.
    			2. Calculate the percentage of required skills found in the resume.
    			3. Give a resumeScore between 0 and 100.
    			4. Give a matchPercentage between 0 and 100.
    			5. Both resumeScore and matchPercentage MUST be integers between 0 and 100.
    			6. Return ONLY valid JSON.
    			7. Do NOT include markdown, explanations, or extra text.

    			JSON Format:

    			{
    			  "resumeScore": 0,
    			  "matchPercentage": 0
    			}
    			""".formatted(
    			        String.join(", ", jobSkills),
    			        resumeText
    			);

        ChatCompletion completion = client.chat().completions().create(

                ChatCompletionCreateParams.builder()
                        .model("llama-3.3-70b-versatile")
                        .addUserMessage(prompt)
                        .build()

        );

        String json = completion.choices()
                .get(0)
                .message()
                .content()
                .orElseThrow(() ->
                new ExternalServiceException("No response from AI service"));
        
        System.out.println(json);

        try {
            return objectMapper.readValue(json, AtsScoreResponse.class);

        } catch (Exception e) {
        	 throw new ExternalServiceException(
        	            "Failed to process AI response", e);
        }
    }
}