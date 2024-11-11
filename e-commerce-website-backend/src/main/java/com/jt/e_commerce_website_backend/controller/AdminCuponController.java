package com.jt.e_commerce_website_backend.controller;

import java.net.http.HttpClient;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.e_commerce_website_backend.model.Cart;
import com.jt.e_commerce_website_backend.model.Cupon;
import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.service.CartService;
import com.jt.e_commerce_website_backend.service.CuponService;
import com.jt.e_commerce_website_backend.service.UserService;

@RestController
@RequestMapping("/api/cupons")
public class AdminCuponController {

    @Autowired
    public CuponService cuponService;

    @Autowired
    public UserService userService;

    @Autowired
    public CartService cartService;

    @PostMapping("/apply")
    public ResponseEntity<Cart> applyCupon(
            @RequestParam String apply,
            @RequestParam String code,
            @RequestParam double orderValue,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Cart cart;
        if (apply.equals("true")) {
            cart = cuponService.applyCupon(code, orderValue, user);
        } else {
            cart = cuponService.removeCupon(code, user);
        }

        return ResponseEntity.ok(cart);

    }

    @PostMapping("/admin/create")
    public ResponseEntity<Cupon> createCupon(@RequestBody Cupon cupon) {
        Cupon createCupon = cuponService.createCupon(cupon);
        return ResponseEntity.ok(createCupon);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteCupon(@PathVariable long id) throws Exception {
        cuponService.deleteCupon(id);
        return ResponseEntity.ok("Cupon deleted Successfully .");
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<Cupon>> getAllCupon() {
        List<Cupon> cupons = cuponService.findAllCupons();
        return new ResponseEntity<>(cupons, HttpStatus.ACCEPTED);
    }

}
