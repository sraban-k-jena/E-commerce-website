package com.jt.e_commerce_website_backend.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.e_commerce_website_backend.model.Product;
import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.model.Wishlist;
import com.jt.e_commerce_website_backend.repository.WishlistRepository;
import com.jt.e_commerce_website_backend.service.WishlistService;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    public WishlistRepository wishlistRepository;

    @Override
    public Wishlist createWishlist(User user) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        return wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist getWishlistByUserId(User user) {
        Wishlist wishlist = wishlistRepository.findByUserId(user.getId());

        if (wishlist == null) {
            wishlist = createWishlist(user);
        }
        return wishlist;
    }

    @Override
    public Wishlist addProductToWishlist(User user, Product product) {
        Wishlist wishlist = getWishlistByUserId(user);

        if (wishlist.getProducts().contains(product)) {
            wishlist.getProducts().remove(product);
        } else
            wishlist.getProducts().add(product);

        return wishlistRepository.save(wishlist);
    }

}