package com.jt.e_commerce_website_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jt.e_commerce_website_backend.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(long id);
}
