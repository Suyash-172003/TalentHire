package com.talenthire.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.talenthire.application.dto.ApplicationAppliedEvent;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String,
            ApplicationAppliedEvent> kafkaTemplate;

    public void send(ApplicationAppliedEvent event) {

        kafkaTemplate.send(
                "application-applied",
                event);

    }
}