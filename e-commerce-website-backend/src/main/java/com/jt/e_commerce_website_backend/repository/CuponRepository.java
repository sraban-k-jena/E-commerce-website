package com.jt.e_commerce_website_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jt.e_commerce_website_backend.model.Cupon;

public interface CuponRepository extends JpaRepository<Cupon, Long> {
    Cupon findByCode(String code);
}
