package com.jt.e_commerce_website_backend.service;

import java.util.List;

import com.jt.e_commerce_website_backend.constant.AccountStatus;
import com.jt.e_commerce_website_backend.model.Seller;

public interface SellerService {

    Seller getSellerProfile(String jwt) throws Exception;

    Seller createSeller(Seller seller) throws Exception;

    Seller getSellerById(long id) throws Exception;

    Seller getSellerByEmail(String email) throws Exception;

    List<Seller> getAllSellers(AccountStatus status);

    Seller updateSeller(long id, Seller seller) throws Exception;

    void deleteSeller(long id) throws Exception;

    Seller verifyEmail(String email, String otp) throws Exception;

    Seller updateSellerAccountStatus(long sellerId, AccountStatus status) throws Exception;
}
