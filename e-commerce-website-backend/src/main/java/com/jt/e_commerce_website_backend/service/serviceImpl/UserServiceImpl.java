package com.jt.e_commerce_website_backend.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.e_commerce_website_backend.config.JwtProvider;
import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.repository.UserRepository;
import com.jt.e_commerce_website_backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("user not Found with email -" + email);
        }
        return user;
    }
}
