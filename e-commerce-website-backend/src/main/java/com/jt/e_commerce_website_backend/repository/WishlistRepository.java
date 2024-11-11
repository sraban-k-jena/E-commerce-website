package com.jt.e_commerce_website_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jt.e_commerce_website_backend.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Wishlist findByUserId(long userId);
}
