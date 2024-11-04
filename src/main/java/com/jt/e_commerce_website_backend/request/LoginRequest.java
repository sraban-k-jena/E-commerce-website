package com.jt.e_commerce_website_backend.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String otp;
}
