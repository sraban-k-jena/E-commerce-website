package com.jt.e_commerce_website_backend.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.e_commerce_website_backend.model.CartItem;
import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.repository.CartItemRepository;
import com.jt.e_commerce_website_backend.service.CartItemService;

// @Service
// public class CartItemServiceImpl implements CartItemService {

//     @Autowired
//     public CartItemRepository cartItemRepository;

//     @Override
//     public CartItem updateCartItem(long userId, long id, CartItem cartItem) throws Exception {
//         CartItem item = findCartItemById(id);

//         User cartItemUser = item.getCart().getUser();

//         if (cartItemUser.getId() == userId) {
//             item.setQuantity(cartItem.getQuantity());
//             item.setMrpPrice(item.getQuantity() * item.getProduct().getMrpPrice());
//             item.setSellingPrice(item.getQuantity() * item.getProduct().getSellingPrice());
//             return cartItemRepository.save(item);
//         } else {
//             throw new IllegalArgumentException("User ID does not match the cart item owner.");
//         }
//     }

//     @Override
//     public void removeCartItem(long userId, long cartItemId) throws Exception {
//         CartItem item = findCartItemById(cartItemId);

//         User cartItemUser = item.getCart().getUser();

//         if (cartItemUser.getId() == userId) {
//             cartItemRepository.delete(item);
//         } else {
//             throw new IllegalArgumentException("You are not delete this item .");
//         }
//     }

//     @Override
//     public CartItem findCartItemById(long id) throws Exception {
//         return cartItemRepository.findById(id).orElseThrow(() -> new Exception("Cart item not found with id :" + id));
//     }

// }
@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartItem updateCartItem(long userId, long id, CartItem cartItem) throws Exception {
        CartItem item = findCartItemById(id);
        if (item.getCart().getUser().getId() == userId) {
            item.setQuantity(cartItem.getQuantity());
            item.setSellingPrice(cartItem.getQuantity() * item.getProduct().getSellingPrice());
            return cartItemRepository.save(item);
        } else {
            throw new IllegalArgumentException("User ID does not match the cart item owner.");
        }
    }

    @Override
    public void removeCartItem(long userId, long cartItemId) throws Exception {
        CartItem item = findCartItemById(cartItemId);
        if (item.getCart().getUser().getId() == userId) {
            cartItemRepository.delete(item);
        } else {
            throw new IllegalArgumentException("You are not authorized to delete this item.");
        }
    }

    @Override
    public CartItem findCartItemById(long id) throws Exception {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new Exception("Cart item not found with id: " + id));
    }
}
