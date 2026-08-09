package com.talenthire.notification.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.talenthire.notification.dto.InterviewScheduledEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewConsumer {

    private final EmailService emailService;

    @KafkaListener(
            topics = "interview-scheduled",
            containerFactory =
                    "interviewKafkaListenerContainerFactory"
    )
    public void consume(InterviewScheduledEvent event) {

        System.out.println(
                "========== INTERVIEW EVENT RECEIVED =========="
        );

        System.out.println(
                "Name: " + event.getCandidateName()
        );

        System.out.println(
                "Email: " + event.getCandidateEmail()
        );

        System.out.println(
                "Job: " + event.getJobTitle()
        );

        System.out.println(
                "Date: " + event.getInterviewDate()
        );

        System.out.println(
                "Meeting Link: " + event.getMeetingLink()
        );

        System.out.println(
                "=============================================="
        );

        emailService.sendInterviewScheduled(event);
    }
}