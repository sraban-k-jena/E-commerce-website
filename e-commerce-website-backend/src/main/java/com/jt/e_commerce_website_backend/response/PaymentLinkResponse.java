package com.jt.e_commerce_website_backend.response;

import lombok.Data;

@Data
public class PaymentLinkResponse {

    private String payment_link_url;
    private String payment_link_id;
}
