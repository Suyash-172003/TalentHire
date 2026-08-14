package com.talenthire.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;

@Configuration
public class GroqConfig {

    @Value("${groq.api.key}")
    private String apiKey;

    @Bean
    public OpenAIClient groqClient() {

        return OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .baseUrl("https://api.groq.com/openai/v1")
                .build();
    }

}