package com.jt.e_commerce_website_backend.request;

import com.jt.e_commerce_website_backend.constant.UserRole;

import lombok.Data;

@Data
public class LoginOtpRequest {
    private String email;
    private String otp;
    private UserRole role;
}
