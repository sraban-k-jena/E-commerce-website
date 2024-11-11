package com.jt.e_commerce_website_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jt.e_commerce_website_backend.model.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {

    PaymentOrder findByPaymentLinkId(String paymentId);
}
