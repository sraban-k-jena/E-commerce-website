package com.jt.e_commerce_website_backend.service;

import java.util.List;
import java.util.Set;

import com.jt.e_commerce_website_backend.constant.OrderStatus;
import com.jt.e_commerce_website_backend.model.Address;
import com.jt.e_commerce_website_backend.model.Cart;
import com.jt.e_commerce_website_backend.model.Order;
import com.jt.e_commerce_website_backend.model.OrderItem;
import com.jt.e_commerce_website_backend.model.User;

public interface OrderService {

    Set<Order> createOrder(User user, Address shippingAddress, Cart cart);

    Order findOrderById(long id) throws Exception;

    List<Order> userOrderHistory(long userId);

    List<Order> sellersOrder(long sellerId);

    Order updateOrderStatus(long orderId, OrderStatus orderStatus) throws Exception;

    Order cancelOrder(long orderId, User user) throws Exception;

    OrderItem getOrderItemById(long id) throws Exception;
}
