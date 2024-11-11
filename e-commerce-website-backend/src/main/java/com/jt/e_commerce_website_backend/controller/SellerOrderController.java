package com.jt.e_commerce_website_backend.controller;

import java.net.ResponseCache;
import java.net.http.HttpResponse.ResponseInfo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.e_commerce_website_backend.constant.OrderStatus;
import com.jt.e_commerce_website_backend.model.Order;
import com.jt.e_commerce_website_backend.model.Seller;
import com.jt.e_commerce_website_backend.service.OrderService;
import com.jt.e_commerce_website_backend.service.SellerService;

@RestController
@RequestMapping("/api/sellers/orders")
public class SellerOrderController {

    @Autowired
    public OrderService orderService;

    @Autowired
    public SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrderHandler(@RequestHeader("Authorization") String jwt) throws Exception {
        Seller seller = sellerService.getSellerProfile(jwt);
        List<Order> orders = orderService.sellersOrder(seller.getId());

        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{orderId}/status/{orderStatus}")
    public ResponseEntity<Order> updateOrderHandler(
            @RequestHeader("Authorization") String jwt, @PathVariable long orderId,
            @PathVariable OrderStatus orderStatus) throws Exception {

        Order order = orderService.updateOrderStatus(orderId, orderStatus);

        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

}
