package com.jt.e_commerce_website_backend.service;

import com.jt.e_commerce_website_backend.model.Cart;
import com.jt.e_commerce_website_backend.model.CartItem;
import com.jt.e_commerce_website_backend.model.Product;
import com.jt.e_commerce_website_backend.model.User;

public interface CartService {
    CartItem addCartItem(User user, Product product, String size, int quantity);

    Cart findUserCart(User user);
}
