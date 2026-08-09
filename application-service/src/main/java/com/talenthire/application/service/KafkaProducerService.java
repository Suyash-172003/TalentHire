package com.talenthire.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.talenthire.application.dto.ApplicationAppliedEvent;
import com.talenthire.application.dto.InterviewScheduledEvent;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(ApplicationAppliedEvent event) {

        kafkaTemplate.send(
                "application-applied",
                event);
    }

    public void sendInterviewScheduled(
            InterviewScheduledEvent event) {

    	System.out.println("Kafka event"+event);
        kafkaTemplate.send(
                "interview-scheduled",
                event);
    }
}