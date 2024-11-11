package com.jt.e_commerce_website_backend.service.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.jt.e_commerce_website_backend.model.Cart;
import com.jt.e_commerce_website_backend.model.Cupon;
import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.repository.CartRepository;
import com.jt.e_commerce_website_backend.repository.CuponRepository;
import com.jt.e_commerce_website_backend.repository.UserRepository;
import com.jt.e_commerce_website_backend.service.CuponService;
import com.jt.e_commerce_website_backend.service.UserService;

@Service
public class CuponServiceImpl implements CuponService {

    @Autowired
    public CuponRepository cuponRepository;

    @Autowired
    public CartRepository cartRepository;

    @Autowired
    public UserService userService;

    @Autowired
    public UserRepository userRepository;

    @Override
    public Cart applyCupon(String code, double orderValue, User user) throws Exception {
        Cupon cupon = cuponRepository.findByCode(code);

        Cart cart = cartRepository.findByUserId(user.getId());
        if (cupon == null) {
            throw new Exception("cupon not valid ");
        }
        if (user.getUseCupons().contains(cupon)) {
            throw new Exception("Cupon already used .");
        }
        if (orderValue < cupon.getMinimumOrderValue()) {
            throw new Exception("Valid for minimum order Value " + cupon.getMinimumOrderValue());
        }
        if (cupon.isActive() && LocalDate.now().isAfter(cupon.getValidityStartDate())
                && LocalDate.now().isBefore(cupon.getValidityEndDate())) {
            user.getUseCupons().add(cupon);
            userRepository.save(user);

            double discountPrice = (cart.getTotalSellingPrice() * cupon.getDiscountPercentage()) / 100;
            cart.setTotalSellingPrice(cart.getTotalSellingPrice() - discountPrice);

            cart.setCouponCode(code);
            cartRepository.save(cart);

            return cart;
        }
        throw new Exception("cupon not valid .");
    }

    @Override
    public Cart removeCupon(String code, User user) throws Exception {
        Cupon cupon = cuponRepository.findByCode(code);
        if (cupon == null) {
            throw new Exception("cupon not found .");
        }
        Cart cart = cartRepository.findByUserId(user.getId());
        double discountPrice = (cart.getTotalSellingPrice() * cupon.getDiscountPercentage());

        cart.setTotalSellingPrice(cart.getTotalSellingPrice() + discountPrice);
        cart.setCouponCode(null);
        return cartRepository.save(cart);
    }

    @Override
    public Cupon findCuponById(long id) throws Exception {
        return cuponRepository.findById(id).orElseThrow(() -> new Exception("Cupon not Found ."));
    }

    @Override
    @PreAuthorize("hasRole ('ADMIN')")
    public Cupon createCupon(Cupon cupon) {
        return cuponRepository.save(cupon);
    }

    @Override
    public List<Cupon> findAllCupons() {
        return cuponRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole ('ADMIN')")
    public void deleteCupon(long id) throws Exception {
        findCuponById(id);
        cuponRepository.deleteById(id);
    }

}
