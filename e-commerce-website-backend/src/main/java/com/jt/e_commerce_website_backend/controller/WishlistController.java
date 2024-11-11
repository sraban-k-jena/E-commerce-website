package com.jt.e_commerce_website_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.e_commerce_website_backend.model.Product;
import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.model.Wishlist;
import com.jt.e_commerce_website_backend.service.ProductService;
import com.jt.e_commerce_website_backend.service.UserService;
import com.jt.e_commerce_website_backend.service.WishlistService;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    public WishlistService wishListService;

    @Autowired
    public UserService userService;

    @Autowired
    public ProductService productService;

    @GetMapping
    public ResponseEntity<Wishlist> getWishListByUserId(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Wishlist wishlist = wishListService.getWishlistByUserId(user);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<Wishlist> addProductToWishlist(
            @PathVariable long productId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        Product product = productService.findProductById(productId);
        User user = userService.findUserByJwtToken(jwt);

        Wishlist updateWishlist = wishListService.addProductToWishlist(user, product);

        return ResponseEntity.ok(updateWishlist);

    }
}
