package com.jt.e_commerce_website_backend.service;

import com.jt.e_commerce_website_backend.model.Product;
import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.model.Wishlist;

public interface WishlistService {

    Wishlist createWishlist(User user);

    Wishlist getWishlistByUserId(User user);

    Wishlist addProductToWishlist(User user, Product product);
}
