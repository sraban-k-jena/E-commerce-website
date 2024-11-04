package com.jt.e_commerce_website_backend.model;

import com.jt.e_commerce_website_backend.constant.PaymentStatus;

import lombok.Data;

@Data
public class PaymentDetails {

    private String paymentId;
    private String razorayPaymentLinkId;
    private String razorpayPaymentReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentIdZWSP;
    private PaymentStatus status;
}
