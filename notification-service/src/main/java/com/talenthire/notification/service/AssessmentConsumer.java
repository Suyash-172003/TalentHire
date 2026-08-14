package com.talenthire.notification.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.talenthire.notification.dto.AssessmentAssignedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssessmentConsumer {

    private final EmailService emailService;

    @KafkaListener(
            topics = "assessment-assigned",
            groupId = "assessment-notification-group",
            containerFactory = "assessmentKafkaListenerContainerFactory"
    )
    public void consume(AssessmentAssignedEvent event) {

        System.out.println("========== ASSESSMENT ASSIGNED ==========");

        System.out.println("Name: " + event.getCandidateName());
        System.out.println("Email: " + event.getCandidateEmail());
        System.out.println("Assessment: " + event.getAssessmentTitle());

        System.out.println("=========================================");

        emailService.sendAssessmentAssignedEmail(event);
    }
}