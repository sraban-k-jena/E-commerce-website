package com.jt.e_commerce_website_backend.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.e_commerce_website_backend.constant.OrderStatus;
import com.jt.e_commerce_website_backend.constant.PaymentStatus;
import com.jt.e_commerce_website_backend.model.Address;
import com.jt.e_commerce_website_backend.model.Cart;
import com.jt.e_commerce_website_backend.model.CartItem;
import com.jt.e_commerce_website_backend.model.Order;
import com.jt.e_commerce_website_backend.model.OrderItem;
import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.repository.AddressRepository;
import com.jt.e_commerce_website_backend.repository.OrderItemRepository;
import com.jt.e_commerce_website_backend.repository.OrderRepository;
import com.jt.e_commerce_website_backend.service.OrderService;

@Service
public class OderServiceImpl implements OrderService {

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public AddressRepository addressRepository;

    @Autowired
    public OrderItemRepository orderItemRepository;

    @Override
    public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) {

        if (!user.getAddresses().contains(shippingAddress)) {
            user.getAddresses().add(shippingAddress);

        }

        Address address = addressRepository.save(shippingAddress);

        Map<Long, List<CartItem>> itemsBySeller = cart.getCartItems().stream()
                .collect(Collectors.groupingBy(item -> item.getProduct().getSeller().getId()));

        Set<Order> orders = new HashSet<>();

        for (Map.Entry<Long, List<CartItem>> entry : itemsBySeller.entrySet()) {
            Long sellerId = entry.getKey();
            List<CartItem> items = entry.getValue();

            int totalOrderPrice = items.stream().mapToInt(
                    CartItem::getSellingPrice).sum();
            int totalItem = items.stream().mapToInt(CartItem::getQuantity).sum();

            Order createOrder = new Order();
            createOrder.setUser(user);
            createOrder.setSellerId(sellerId);
            createOrder.setTotalPrice(totalOrderPrice);
            createOrder.setTotalSellingPrice(totalOrderPrice);
            createOrder.setTotalItem(totalItem);
            createOrder.setShippingAddress(address);
            createOrder.setOrderStatus(OrderStatus.PENDING);
            createOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);

            Order saveOrder = orderRepository.save(createOrder);
            orders.add(saveOrder);

            List<OrderItem> orderItems = new ArrayList<>();

            for (CartItem item : items) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(saveOrder);
                orderItem.setMrpPrice(item.getMrpPrice());
                orderItem.setProduct(item.getProduct());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setSize(item.getSize());
                orderItem.setUserId(item.getUserId());
                orderItem.setSellingPrice(item.getSellingPrice());

                saveOrder.getOrderItems().add(orderItem);

                OrderItem savOrderItem = orderItemRepository.save(orderItem);
                orderItems.add(savOrderItem);
            }
        }
        return orders;
    }

    @Override
    public Order findOrderById(long id) throws Exception {
        return orderRepository.findById(id).orElseThrow(() -> new Exception("Order not Found ."));
    }

    @Override
    public List<Order> userOrderHistory(long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> sellersOrder(long sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }

    @Override
    public Order updateOrderStatus(long orderId, OrderStatus orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(long orderId, User user) throws Exception {
        // Find the order by ID
        Order order = findOrderById(orderId);

        // Ensure the order exists
        if (order == null) {
            throw new Exception("Order with ID " + orderId + " not found.");
        }

        // Ensure the order has a user associated with it and check authorization
        if (order.getUser() == null || user == null || user.getId() != order.getUser().getId()) {
            throw new Exception("You do not have access to this order.");
        }

        // Set order status to CANCELLED and save
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public OrderItem getOrderItemById(long id) throws Exception {
        return orderItemRepository.findById(id).orElseThrow(() -> new Exception("Order Item not Exist ."));
    }

}
