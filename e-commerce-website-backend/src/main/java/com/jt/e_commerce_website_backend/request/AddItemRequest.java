package com.jt.e_commerce_website_backend.request;

import lombok.Data;

@Data
public class AddItemRequest {
    private String size;
    private int quantity;
    private int productId;
}
