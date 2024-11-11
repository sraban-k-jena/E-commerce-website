package com.jt.e_commerce_website_backend.service;

import java.util.List;

import com.jt.e_commerce_website_backend.model.Cart;
import com.jt.e_commerce_website_backend.model.Cupon;
import com.jt.e_commerce_website_backend.model.User;

public interface CuponService {

    Cart applyCupon(String code, double orderValue, User user) throws Exception;

    Cart removeCupon(String code, User user) throws Exception;

    Cupon findCuponById(long id) throws Exception;

    Cupon createCupon(Cupon cupon);

    List<Cupon> findAllCupons();

    void deleteCupon(long id) throws Exception;
}
