package com.talenthire.assessment.service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.talenthire.assessment.dto.AssessmentAssignedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, AssessmentAssignedEvent>
            kafkaTemplate;

    public void send(AssessmentAssignedEvent event) {

        kafkaTemplate.send(
                "assessment-assigned",
                event
        );
    }
}