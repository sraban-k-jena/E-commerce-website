package com.jt.e_commerce_website_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jt.e_commerce_website_backend.model.VerificationCode;

public interface VerfificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    VerificationCode findByEmail(String email);

    VerificationCode findByOtp(String otp);
}
