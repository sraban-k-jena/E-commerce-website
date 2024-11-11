package com.jt.e_commerce_website_backend.service;

import com.jt.e_commerce_website_backend.constant.UserRole;
import com.jt.e_commerce_website_backend.request.LoginRequest;
import com.jt.e_commerce_website_backend.response.AuthResponse;
import com.jt.e_commerce_website_backend.response.SignupRequest;

public interface AuthService {

    void sentLoginOtp(String email, UserRole role) throws Exception;

    String createUser(SignupRequest req) throws Exception;

    AuthResponse signing(LoginRequest req) throws Exception;
}
