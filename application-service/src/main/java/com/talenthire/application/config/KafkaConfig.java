package com.talenthire.application.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic applicationAppliedTopic() {
        return new NewTopic(
                "application-applied",
                1,
                (short) 1
        );
    }
}