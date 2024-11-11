package com.jt.e_commerce_website_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.e_commerce_website_backend.model.Order;
import com.jt.e_commerce_website_backend.model.PaymentOrder;
import com.jt.e_commerce_website_backend.model.Seller;
import com.jt.e_commerce_website_backend.model.SellerReport;
import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.response.ApiResponse;
import com.jt.e_commerce_website_backend.response.PaymentLinkResponse;
import com.jt.e_commerce_website_backend.service.OrderService;
import com.jt.e_commerce_website_backend.service.PaymentService;
import com.jt.e_commerce_website_backend.service.SellerReportService;
import com.jt.e_commerce_website_backend.service.SellerService;
import com.jt.e_commerce_website_backend.service.TransactionService;
import com.jt.e_commerce_website_backend.service.UserService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    public UserService userService;

    @Autowired
    public PaymentService paymentService;

    @Autowired
    public SellerService sellerService;

    @Autowired
    public OrderService orderService;

    @Autowired
    public TransactionService transactionService;

    @Autowired
    public SellerReportService sellerReportService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> paymentSuccessHandler(
            @PathVariable String paymentId,
            @RequestParam String paymentLinkId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        PaymentLinkResponse paymentLinkResponse;

        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);

        boolean paymentSuccess = paymentService.proceedPayementOrder(paymentOrder, paymentId, paymentLinkId);

        if (paymentSuccess) {

            for (Order order : paymentOrder.getOrders()) {
                transactionService.createTransaction(order);
                Seller seller = sellerService.getSellerById(order.getSellerId());
                SellerReport report = sellerReportService.getSellerReport(seller);
                report.setTotalOrders(report.getTotalOrders() + 1);
                report.setTotalEarning(report.getTotalEarning() + order.getTotalSellingPrice());
                report.setTotalSales(report.getTotalSales() + order.getOrderItems().size());
                sellerReportService.updateSellerReport(report);
            }
        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Payment Successful ");

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
