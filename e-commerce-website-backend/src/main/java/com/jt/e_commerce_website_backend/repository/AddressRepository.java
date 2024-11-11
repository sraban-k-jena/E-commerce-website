package com.jt.e_commerce_website_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jt.e_commerce_website_backend.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
