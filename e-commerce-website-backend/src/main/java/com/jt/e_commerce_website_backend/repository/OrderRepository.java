package com.jt.e_commerce_website_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jt.e_commerce_website_backend.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(long userId);

    List<Order> findBySellerId(long sellerId);
}
