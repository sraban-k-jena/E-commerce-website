package com.jt.e_commerce_website_backend.service;

import java.util.Set;

import com.jt.e_commerce_website_backend.model.Order;
import com.jt.e_commerce_website_backend.model.PaymentOrder;
import com.jt.e_commerce_website_backend.model.User;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;

public interface PaymentService {

        PaymentOrder createOrder(User user, Set<Order> orders);

        PaymentOrder getPaymentOrderById(long orderId) throws Exception;

        PaymentOrder getPaymentOrderByPaymentId(String orderId) throws Exception;

        Boolean proceedPayementOrder(PaymentOrder paymentOrder,
                        String paymentId,
                        String PaymentLinkId) throws RazorpayException;

        PaymentLink createRozerPaymentLink(User user,
                        long amount,
                        long orderId) throws RazorpayException;

        String createStringPaymentLink(User user, long amount, long orderId);
}
