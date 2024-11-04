package com.jt.e_commerce_website_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jt.e_commerce_website_backend.constant.UserRole;
import com.jt.e_commerce_website_backend.repository.UserRepository;
import com.jt.e_commerce_website_backend.request.LoginOtpRequest;
import com.jt.e_commerce_website_backend.request.LoginRequest;
import com.jt.e_commerce_website_backend.response.ApiResponse;
import com.jt.e_commerce_website_backend.response.AuthResponse;
import com.jt.e_commerce_website_backend.response.SignupRequest;
import com.jt.e_commerce_website_backend.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandle(@RequestBody SignupRequest request) throws Exception {
        String jwt = authService.createUser(request);
        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setMessage("Register Success .");
        response.setRole(UserRole.ROLE_CUSTOMER);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody LoginOtpRequest request) throws Exception {

        authService.sentLoginOtp(request.getEmail(), request.getRole());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Otp Send Successfully .");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) throws Exception {
        AuthResponse authResponse = authService.signing(req);
        return ResponseEntity.ok(authResponse);
    }
}
