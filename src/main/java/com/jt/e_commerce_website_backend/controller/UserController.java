package com.jt.e_commerce_website_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.service.UserService;

@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping("/users/profile")
    public ResponseEntity<User> createUserHandle(@RequestHeader("Authorization") String authorizationHeader)
            throws Exception {
        // Extract JWT token from the Authorization header
        String jwt = authorizationHeader.substring(7); // Remove "Bearer " prefix
        User user = userService.findUserByJwtToken(jwt);
        return ResponseEntity.ok(user);
    }

}
