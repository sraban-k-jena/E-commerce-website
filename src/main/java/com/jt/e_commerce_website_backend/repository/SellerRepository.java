package com.jt.e_commerce_website_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jt.e_commerce_website_backend.constant.AccountStatus;
import com.jt.e_commerce_website_backend.model.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByEmail(String email);

    List<Seller> findByAccountStatus(AccountStatus status);
}
