package com.talenthire.notification.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.talenthire.notification.dto.ApplicationAppliedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationConsumer {
	
	private final EmailService emailService;

    @KafkaListener(
            topics = "application-applied",
            groupId = "notification-group"
    )
    public void consume(ApplicationAppliedEvent event) {
    	

        System.out.println("========== EVENT RECEIVED ==========");
        System.out.println("Name: " + event.getCandidateName());
        System.out.println("Email: " + event.getCandidateEmail());
        System.out.println("Job: " + event.getJobTitle());
        System.out.println("====================================");

        emailService.send(event);
    }
}
