package com.jt.e_commerce_website_backend.response;

import com.jt.e_commerce_website_backend.constant.UserRole;

import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserRole role;
}
