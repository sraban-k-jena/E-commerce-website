package com.jt.e_commerce_website_backend.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.e_commerce_website_backend.model.Cart;
import com.jt.e_commerce_website_backend.model.CartItem;
import com.jt.e_commerce_website_backend.model.Product;
import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.repository.CartItemRepository;
import com.jt.e_commerce_website_backend.repository.CartRepository;
import com.jt.e_commerce_website_backend.service.CartService;

import jakarta.transaction.Transactional;

// @Service
// public class CartServiceImpl implements CartService {

//     @Autowired
//     public CartItemRepository cartItemRepository;

//     @Autowired
//     public CartRepository cartRepository;

//     @Override
//     public CartItem addCartItem(User user, Product product, String size, int quantity) {
//         Cart cart = findUserCart(user);
//         CartItem isPresent = cartItemRepository.findByCartAndProductAndSize(cart, product, size);

//         if (isPresent == null) {
//             CartItem cartItem = new CartItem();
//             cartItem.setProduct(product);
//             cartItem.setQuantity(quantity);
//             cartItem.setId(user.getId());
//             cartItem.setSize(size);

//             int totalPrice = quantity * product.getSellingPrice();
//             cartItem.setSellingPrice(totalPrice);

//             cart.getCartItems().add(cartItem);
//             cartItem.setCart(cart);

//             return cartItemRepository.save(cartItem);
//         }
//         return isPresent;
//     }

//     @Override
//     public Cart findUserCart(User user) {
//         Cart cart = cartRepository.findByUserId(user.getId());

//         int totalPrice = 0;
//         int totalDiscountPrice = 0;
//         int totalItem = 0;

//         for (CartItem cartItem : cart.getCartItems()) {
//             totalPrice += cartItem.getMrpPrice();
//             totalDiscountPrice += cartItem.getSellingPrice();
//             totalItem += cartItem.getQuantity();
//         }

//         cart.setTotalMrpPrice(totalPrice);
//         cart.setTotalItem(totalItem);
//         cart.setTotalSellingPrice(totalDiscountPrice);
//         cart.setDiscount(calculateDiscountPercentage(totalPrice, totalDiscountPrice));
//         cart.setTotalItem(totalItem);
//         return null;

//     }

//     private int calculateDiscountPercentage(Integer mrpPrice, int sellingPrice) {
//         if (mrpPrice == null || mrpPrice <= 0) {
//             throw new IllegalArgumentException("MRP Price must be greater than 0");
//         }
//         double discount = mrpPrice - sellingPrice;
//         return (int) ((discount / mrpPrice) * 100);
//     }

// }

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    @Transactional
    public CartItem addCartItem(User user, Product product, String size, int quantity) {
        Cart cart = findUserCart(user);
        CartItem existingItem = cartItemRepository.findByCartAndProductAndSize(cart, product, size);

        if (existingItem == null) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUserId(user.getId());
            cartItem.setSize(size);
            cartItem.setSellingPrice(quantity * product.getSellingPrice());
            cartItem.setMrpPrice(quantity * product.getMrpPrice());
            cart.getCartItems().add(cartItem);
            cartItem.setCart(cart);

            return cartItemRepository.save(cartItem);
        }
        return existingItem;
    }

    @Override
    public Cart findUserCart(User user) {
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }
        return cart;
    }
}
