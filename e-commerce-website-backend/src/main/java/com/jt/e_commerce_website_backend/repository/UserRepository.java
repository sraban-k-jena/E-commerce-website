package com.jt.e_commerce_website_backend.repository;

import com.jt.e_commerce_website_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
