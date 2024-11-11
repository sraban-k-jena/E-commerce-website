package com.jt.e_commerce_website_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.e_commerce_website_backend.constant.AccountStatus;
import com.jt.e_commerce_website_backend.model.Seller;
import com.jt.e_commerce_website_backend.service.SellerService;

@RestController
@RequestMapping("/api")
public class AdminHomeController {

    @Autowired
    public SellerService sellerService;

    @PatchMapping("/seller/{id}/status/{status}")
    public ResponseEntity<Seller> updateSeller(@PathVariable long id, @PathVariable AccountStatus status)
            throws Exception {
        Seller updatSeller = sellerService.updateSellerAccountStatus(id, status);
        return ResponseEntity.ok(updatSeller);
    }

}
