package com.talenthire.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthire.auth.entity.PasswordResetOtp;

public interface PasswordResetOtpRepository extends JpaRepository<PasswordResetOtp, Integer> {

Optional<PasswordResetOtp> findByEmail(String email);

void deleteByEmail(String email);

}