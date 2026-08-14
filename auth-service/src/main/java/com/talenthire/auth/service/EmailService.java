package com.talenthire.auth.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOtp(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("TalentHire Password Reset");

        message.setText(
                "Your OTP is " + otp +
                "\n\nThis OTP is valid for 5 minutes.");

        mailSender.send(message);
    }
}
