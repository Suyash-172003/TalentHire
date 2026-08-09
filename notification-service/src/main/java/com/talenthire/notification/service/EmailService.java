package com.talenthire.notification.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.talenthire.notification.dto.ApplicationAppliedEvent;
import com.talenthire.notification.dto.AssessmentAssignedEvent;
import com.talenthire.notification.dto.InterviewScheduledEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void send(ApplicationAppliedEvent event) {

    	  SimpleMailMessage message = new SimpleMailMessage();

          message.setTo(event.getCandidateEmail());

          message.setSubject("Application Submitted Successfully - TalentHire");

          message.setText(
                  "Dear " + event.getCandidateName() + ",\n\n"
                  + "Your application has been submitted successfully.\n\n"
                  + "Job: " + event.getJobTitle() + "\n\n"
                  + "The recruiter will review your application and you will be notified "
                  + "if you are shortlisted for the next stage.\n\n"
                  + "Thank you for using TalentHire.\n\n"
                  + "Regards,\n"
                  + "TalentHire Team"
          );

          mailSender.send(message);
    }
    
    
    public void sendAssessmentAssignedEmail(
            AssessmentAssignedEvent event) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(event.getCandidateEmail());

        message.setSubject(
                "Assessment Assigned - TalentHire"
        );

        message.setText(
                "Dear " + event.getCandidateName() + ",\n\n"
                + "An assessment has been assigned to you.\n\n"
                + "Assessment: " + event.getAssessmentTitle() + "\n\n"
                + "Please log in to TalentHire and complete the assessment.\n\n"
                + "Regards,\n"
                + "TalentHire Team"
        );

        mailSender.send(message);
    }



	    public void sendInterviewScheduled(
	            InterviewScheduledEvent event) {

	        SimpleMailMessage message =
	                new SimpleMailMessage();
	        message.setTo(event.getCandidateEmail());
	        message.setSubject(
	                "Interview Scheduled - " +
	                event.getJobTitle());
	        message.setText(
	                "Hello " + event.getCandidateName() + ",\n\n" +

	                "Your interview has been scheduled.\n\n" +

	                "Job: " + event.getJobTitle() + "\n" +

	                "Interview Date: " +
	                event.getInterviewDate() + "\n\n" +

	                "Meeting Link: " +
	                event.getMeetingLink() + "\n\n" +

	                "Please join the interview at the scheduled time.\n\n" +
	                "Best regards,\n" +
	                "TalentHire"
	        );

	        mailSender.send(message);
		
	}
}
