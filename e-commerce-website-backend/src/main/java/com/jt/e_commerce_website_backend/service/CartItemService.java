package com.jt.e_commerce_website_backend.service;

import com.jt.e_commerce_website_backend.model.CartItem;

public interface CartItemService {
    CartItem updateCartItem(long userId, long id, CartItem cartItem) throws Exception;

    void removeCartItem(long userId, long cartItemId) throws Exception;

    CartItem findCartItemById(long id) throws Exception;
}
