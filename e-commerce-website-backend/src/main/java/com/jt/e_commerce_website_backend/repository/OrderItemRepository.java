package com.jt.e_commerce_website_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jt.e_commerce_website_backend.model.Order;
import com.jt.e_commerce_website_backend.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
