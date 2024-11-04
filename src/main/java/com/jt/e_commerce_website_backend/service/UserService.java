package com.jt.e_commerce_website_backend.service;

import com.jt.e_commerce_website_backend.model.User;

public interface UserService {

    User findUserByJwtToken(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;
}
