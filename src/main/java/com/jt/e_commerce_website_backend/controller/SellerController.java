package com.jt.e_commerce_website_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jt.e_commerce_website_backend.repository.VerfificationCodeRepository;
import com.jt.e_commerce_website_backend.request.LoginRequest;
import com.jt.e_commerce_website_backend.response.AuthResponse;
import com.jt.e_commerce_website_backend.service.AuthService;
import com.jt.e_commerce_website_backend.service.SellerService;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    @Autowired
    public SellerService sellerService;

    @Autowired
    public VerfificationCodeRepository verfificationCodeRepository;

    @Autowired
    public AuthService authService;

    // @PostMapping("/sent/login-otp")
    // public ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody
    // VerificationCode request) throws Exception {

    // authService.sentLoginOtp(request.getEmail());
    // ApiResponse apiResponse = new ApiResponse();
    // apiResponse.setMessage("Otp Send Successfully .");
    // return ResponseEntity.ok(apiResponse);
    // }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(@RequestBody LoginRequest req) throws Exception {

        String otp = req.getOtp();
        String email = req.getEmail();

        req.setEmail("seller_" + email);
        AuthResponse authResponse = authService.signing(req);

        return ResponseEntity.ok(authResponse);
    }
}
