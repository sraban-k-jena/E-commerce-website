package com.jt.e_commerce_website_backend.controller;

import java.util.List;
import java.util.Set;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.e_commerce_website_backend.constant.PaymentMethod;
import com.jt.e_commerce_website_backend.model.Address;
import com.jt.e_commerce_website_backend.model.Cart;
import com.jt.e_commerce_website_backend.model.Order;
import com.jt.e_commerce_website_backend.model.OrderItem;
import com.jt.e_commerce_website_backend.model.PaymentOrder;
import com.jt.e_commerce_website_backend.model.Seller;
import com.jt.e_commerce_website_backend.model.SellerReport;
import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.repository.PaymentOrderRepository;
import com.jt.e_commerce_website_backend.response.PaymentLinkResponse;
import com.jt.e_commerce_website_backend.service.CartService;
import com.jt.e_commerce_website_backend.service.OrderService;
import com.jt.e_commerce_website_backend.service.PaymentService;
import com.jt.e_commerce_website_backend.service.SellerReportService;
import com.jt.e_commerce_website_backend.service.SellerService;
import com.jt.e_commerce_website_backend.service.UserService;
import com.razorpay.PaymentLink;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    public OrderService orderService;

    @Autowired
    public UserService userService;

    @Autowired
    public CartService cartService;

    @Autowired
    public SellerService sellerService;

    @Autowired
    public SellerReportService sellerReportService;

    @Autowired
    public PaymentService paymentService;

    @Autowired
    public PaymentOrderRepository paymentOrderRepository;

    @PostMapping
    public ResponseEntity<PaymentLinkResponse> createOrdeHandler(
            @RequestBody Address shippingAddress,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findUserCart(user);
        Set<Order> orders = orderService.createOrder(user, shippingAddress, cart);
        PaymentOrder paymentOrder = paymentService.createOrder(user, orders);
        PaymentLinkResponse res = new PaymentLinkResponse();

        if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {
            PaymentLink payment = paymentService.createRozerPaymentLink(user, paymentOrder.getAmount(),
                    paymentOrder.getId());

            String paymentUrl = payment.get("short_url");
            String paymentUrlId = payment.get("id");

            res.setPayment_link_url(paymentUrl);

            paymentOrder.setPaymentLinkId(paymentUrlId);
            paymentOrderRepository.save(paymentOrder);
        }

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order orders = orderService.findOrderById(orderId);

        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @GetMapping("/item/{orderItemId}")
    public ResponseEntity<OrderItem> getPrderItemById(@PathVariable long orderItemId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        OrderItem orderItem = orderService.getOrderItemById(orderItemId);
        return new ResponseEntity<>(orderItem, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(
            @PathVariable long orderId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.cancelOrder(orderId, user);

        Seller seller = sellerService.getSellerById(order.getSellerId());
        SellerReport report = sellerReportService.getSellerReport(seller);

        report.setCancelOrders(report.getCancelOrders() + 1);
        report.setTotalRefunds(report.getTotalRefunds() + order.getTotalSellingPrice());
        sellerReportService.updateSellerReport(report);

        return ResponseEntity.ok(order);
    }

}
